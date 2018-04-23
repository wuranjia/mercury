package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.common.utils.MapUtils;
import com.hy.lang.mercury.dao.SimBaseMapper;
import com.hy.lang.mercury.pojo.*;
import com.hy.lang.mercury.resource.req.SimBaseReq;
import com.hy.lang.mercury.resource.resp.SimBaseResp;
import com.hy.lang.mercury.resource.resp.SimMatrixResp;
import com.hy.lang.mercury.service.SimAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimService implements SimAble {

    @Autowired
    private SimBaseMapper simBaseMapper;

    @Override
    public PageList<SimBase> list(SimBaseReq req) {
        int total = simBaseMapper.countByParams(req);
        List<SimBase> list = simBaseMapper.selectByParams(req);
        PageList<SimBase> pageList = new PageList<SimBase>();
        pageList.setCurrent(req.getPage());
        pageList.setPageSize(req.getLimit());
        pageList.setDraw(req.getDraw());
        pageList.setTotal(total);
        pageList.setItems(list);
        return pageList;
    }

    @Override
    public void importCsv(String path, Long userId) {


        File file = new File(path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            List<SimBase> inserts = new ArrayList<SimBase>();
            while ((line = reader.readLine()) != null) {

                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String simStr = item[0];
                Long sim = Long.valueOf(simStr);
                String iccid = item[1];
                String imsi = item[2];
                String service = item[3];
                String taocan = item[4];
                SimBase simBase = new SimBase(sim, iccid, imsi, service, taocan, userId);
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                SimBaseReq req = new SimBaseReq();
                req.setSim(sim);

                inserts.add(simBase);
/*                List<SimBase> list = simBaseMapper.selectByParams(req);
                if (list != null && !list.isEmpty()) {
                    for (SimBase base : list) {
                        simBaseMapper.deleteByPrimaryKey(base.getId());
                    }
                }*/
                //simBaseMapper.insert(simBase);
                if (inserts.size() >= 1000) {
                    simBaseMapper.batchInsert(inserts);
                    inserts.clear();
                }
            }
            if (inserts.size() != 0) {
                simBaseMapper.batchInsert(inserts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SimMatrixResp matrix(Long userId) {
        int d1 = simBaseMapper.count(MapUtils.buildKeyValueMap("userId", userId));
        int d2 = d1; // TODO: 18/4/18 库存还未做，暂时写死
        int d3 = 0;
        int d4 = simBaseMapper.count(MapUtils.buildKeyValueMap("userId", userId, "activateStatus", ActivateStatus.激活.getCode()));
        int d5 = d1 - d4;
        int d6 = simBaseMapper.count(MapUtils.buildKeyValueMap("userId", userId, "overFlow", OverFlow.超流量.getCode()));
        int d7 = simBaseMapper.count(MapUtils.buildKeyValueMap("userId", userId, "overSms", OverSms.超短信.getCode()));
        int d8 = simBaseMapper.count(MapUtils.buildKeyValueMap("userId", userId, "flagNearExpire", FlagExpire.将过期.getCode()));
        int d9 = simBaseMapper.count(MapUtils.buildKeyValueMap("userId", userId, "flagExpire", FlagExpire.过期.getCode()));
        int d10 = simBaseMapper.count(MapUtils.buildKeyValueMap("userId", userId, "memo", SimMemo.移除.getCode()));

        SimMatrixResp matrixResp = new SimMatrixResp();
        matrixResp.setD1(String.valueOf(d1));
        matrixResp.setD2(String.valueOf(d2));
        matrixResp.setD3(String.valueOf(d3));
        matrixResp.setD4(String.valueOf(d4));
        matrixResp.setD5(String.valueOf(d5));
        matrixResp.setD6(String.valueOf(d6));
        matrixResp.setD7(String.valueOf(d7));
        matrixResp.setD8(String.valueOf(d8));
        matrixResp.setD9(String.valueOf(d9));
        matrixResp.setD10(String.valueOf(d10));
        return matrixResp;
    }

    @Override
    public PageList<SimBase> indexFlowUse(SimBaseReq req) {
        int total = simBaseMapper.countByParams(req);
        List<SimBase> list = simBaseMapper.selectByParamsOrderByFlow(req);
        PageList<SimBase> pageList = new PageList<SimBase>();
        pageList.setCurrent(req.getPage());
        pageList.setPageSize(req.getLimit());
        pageList.setDraw(req.getDraw());
        pageList.setTotal(total);
        pageList.setItems(list);
        return pageList;
    }

    @Override
    public SimBaseResp indexCardInfo(@NotNull Long userId) {
        int d1 = simBaseMapper.count(MapUtils.buildKeyValueMap("userId", userId));
        int d2 = simBaseMapper.count(MapUtils.buildKeyValueMap("userId", userId, "statusArrearage", StatusArrearage.欠费.getCode()));

        SimBaseResp simBaseResp = new SimBaseResp();
        simBaseResp.setArrearage(d2);
        simBaseResp.setNormal(d1 - d2);
        return simBaseResp;
    }

    @Override
    public void assign(String[] cards, String userId) {
        simBaseMapper.assign(cards,userId);
    }

}
