package com.hy.lang.mercury.client.cmiot;

import cn.iot.api.sdk.dto.GprsDto;
import cn.iot.api.sdk.response.*;
import com.hy.lang.mercury.common.utils.DateTimeUtils;
import com.hy.lang.mercury.common.utils.MapUtils;
import com.hy.lang.mercury.common.utils.StringUtils;
import com.hy.lang.mercury.dao.SimBaseMapper;
import com.hy.lang.mercury.pojo.SimBase;
import com.hy.lang.mercury.pojo.StatusDeliver;
import com.hy.lang.mercury.pojo.StatusOnline;
import com.hy.lang.mercury.resource.req.SimBaseReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);


    @Autowired
    private ClientProxy clientProxy;
    @Autowired
    private SimBaseMapper simBaseMapper;
    @Autowired
    private RefreshProcess refreshProcess;

    @PostConstruct
    public void ScheduleService() {
        refreshProcess.start();
    }


    public void doingBatch(String sim, String yyyyMMdd) {
        if (StringUtils.isNotBlank(sim) && StringUtils.isNotBlank(yyyyMMdd)) {
            SimBaseReq req = new SimBaseReq();
            req.setSim(Long.valueOf(sim));
            List<SimBase> list = simBaseMapper.selectByParams(req);
            doing(sim, yyyyMMdd, (list != null && list.size() > 0) ? list.get(0) : null);
            simBaseMapper.updateCal(MapUtils.buildKeyValueMap("simId", sim, "calDay", yyyyMMdd));
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        yyyyMMdd = DateTimeUtils.dateToString(calendar.getTime(), DateTimeUtils.yyyyMMdd);
        //捞取记录，一条一条同步
        int count = simBaseMapper.countByCal(yyyyMMdd);
        logger.info("doingBatch totalRecord = " + count);
        int index = 0;
        while (true) {
            index++;
            List<SimBase> list = simBaseMapper.selectByCal(yyyyMMdd);
            if (list == null || (list != null && list.isEmpty())) {
                logger.info("結束批次,yyyyMMdd = " + yyyyMMdd);
                break;
            }
            for (SimBase simBase : list) {
                String simNo = String.valueOf(simBase.getSimId());
                int row = simBaseMapper.updateCal(MapUtils.buildKeyValueMap("simId", simNo, "calDay", yyyyMMdd));
                logger.info("updateCal row = " + row + ", sim = " + simNo);
                refreshProcess.addEle(new ScheduleService.SingleThreed(simNo, yyyyMMdd, simBase));
            }
            logger.info("loop:" + index + "; num = " + index * 1000);
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    class SingleThreed extends Thread {
        String sim;
        String queryDate;
        SimBase simBase;

        SingleThreed(String sim, String queryDate, SimBase simBase) {
            this.sim = sim;
            this.queryDate = queryDate;
            this.simBase = simBase;
        }

        @Override
        public void run() {
            doing(sim, queryDate, simBase);
        }
    }

    /**
     * @param sim
     * @param yyyyMMdd
     */
    public void doing(String sim, String yyyyMMdd, SimBase simBase) {
        if (simBase == null) return;
        //套餐

        //总流量//使用流量//剩余
        GprsRealTimeInfoResponse gprsUsedInfoSingleResponse = clientProxy.套餐内GPRS流量使用情况实时查询(sim);
        if (gprsUsedInfoSingleResponse != null && isSuccess(gprsUsedInfoSingleResponse.getStatus())) {
            GprsDto gprs = gprsUsedInfoSingleResponse.getResult().get(0).getGprs().get(0);
            simBase.setFlowTotal(StringUtils.isNotBlank(gprs.getTotal()) ? new BigDecimal(gprs.getTotal()) : new BigDecimal(0));
            simBase.setFlowUse(StringUtils.isNotBlank(gprs.getUsed()) ? new BigDecimal(gprs.getUsed()) : new BigDecimal(0));
            simBase.setFlowLess(StringUtils.isNotBlank(gprs.getLeft()) ? new BigDecimal(gprs.getLeft()) : new BigDecimal(0));
        }


        //消耗短信
        SmsUsedInfoSingleResponse smsUsedInfoSingleResponse = clientProxy.用户当月短信查询(sim);
        if (smsUsedInfoSingleResponse != null && isSuccess(smsUsedInfoSingleResponse.getStatus())) {
            String num = smsUsedInfoSingleResponse.getResult().get(0).getSms();
            simBase.setSmsUse(Long.valueOf(num));
        }

        //开卡日期  预计到期时间
        QueryCardLifecycleResponse queryCardLifecycleResponse = clientProxy.物联卡生命周期查询(sim);
        if (queryCardLifecycleResponse != null && isSuccess(queryCardLifecycleResponse.getStatus())) {
            String ot = queryCardLifecycleResponse.getResult().get(0).getOpentime();
            Date openDate = DateTimeUtils.format(ot, DateTimeUtils.yyyyMMdd);
            Date limitDate = DateTimeUtils.getLimitDate(openDate); // TODO: 18/4/18
            simBase.setOpenDate(openDate);
            simBase.setLimitDate(limitDate);
        }
        //已开通服务

        //激活日期

        //状态

        //开关机
        OnAndOffRealSingleResponse onAndOffRealSingleResponse = clientProxy.开关机信息实时查询(sim);
        if (onAndOffRealSingleResponse != null && isSuccess(onAndOffRealSingleResponse.getStatus())) {
            if (onAndOffRealSingleResponse.getResult().get(0).getStatus().equals("0")) {
                simBase.setOpenFlag(StatusDeliver.关机.getCode());
            } else {
                simBase.setOpenFlag(StatusDeliver.开机.getCode());
            }
        }
        //本月消耗流量
        simBase.setFlowUseMonth(simBase.getFlowUse());

        //设备状态
        GprsRealSingleResponse gprsRealTimeInfoResponse = clientProxy.在线信息实时查询(sim);
        if (gprsRealTimeInfoResponse != null && isSuccess(gprsRealTimeInfoResponse.getStatus())) {
            if (gprsRealTimeInfoResponse.getResult().get(0).getGPRSSTATUS().equals("00")) {
                simBase.setStatusOnline(StatusOnline.离线.getCode());
            } else {
                simBase.setStatusOnline(StatusOnline.在线.getCode());
            }
        }
        //是否欠费

        simBaseMapper.updateByPrimaryKey(simBase);
    }

    private boolean isSuccess(String status) {
        return "0".equals(status);
    }
}
