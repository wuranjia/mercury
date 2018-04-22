package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.client.cmpp.mina.cmpp.client.SmsSendService;
import com.hy.lang.mercury.client.ws.SmsStatus;
import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.dao.SmsDeliverMapper;
import com.hy.lang.mercury.dao.SmsInfoMapper;
import com.hy.lang.mercury.dao.SmsViewMapper;
import com.hy.lang.mercury.pojo.SmsDeliver;
import com.hy.lang.mercury.pojo.SmsInfo;
import com.hy.lang.mercury.pojo.SmsView;
import com.hy.lang.mercury.pojo.SmsViewType;
import com.hy.lang.mercury.resource.req.SmsDeliverReq;
import com.hy.lang.mercury.resource.req.SmsInfoReq;
import com.hy.lang.mercury.resource.req.SmsViewReq;
import com.hy.lang.mercury.service.SmsAble;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsService implements SmsAble {

    @Autowired
    private SmsInfoMapper smsInfoMapper;

    @Autowired
    private SmsDeliverMapper smsDeliverMapper;

    @Autowired
    private SmsSendService smsSendService;

    @Autowired
    private SmsViewMapper smsViewMapper;

    /**
     * 发消息
     *
     * @param receiveIds 逗号分隔
     * @param smsContent 消息内容
     * @param userId     用户ID
     * @return
     */
    @Override
    public int sendSms(String receiveIds, String smsContent, Long userId) {
        String[] receivedArray = StringUtils.split(receiveIds, Constants.分号);
        if (userId == null) userId = Constants.L_负_1;
        //调用client，发送消息
        int i = 0;
        try {
            for (String dest : receivedArray) {
                SmsInfo smsInfo = new SmsInfo(dest, smsContent, userId, null);
                int seq = smsInfoMapper.insert(smsInfo);
                smsViewMapper.insert(new SmsView(smsInfo.getSmsId(),dest, smsContent, SmsViewType.发送, SmsStatus.发送中.name()));
                int requestId = smsSendService.send(dest, smsInfo.getSmsId(), smsContent);
                smsInfoMapper.updateSmsOtherNo(smsInfo.getSmsId(), String.valueOf(requestId));
                i = i + seq;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //组装消息体，落地
        return i;
    }

    @Override
    public PageList<SmsInfo> listSms(SmsInfoReq smsInfoReq, Long userId) {
        smsInfoReq.setUserId(userId);
        int total = smsInfoMapper.countByParams(smsInfoReq);
        //查询
        List<SmsInfo> list = smsInfoMapper.selectByParams(smsInfoReq);
        PageList<SmsInfo> pageList = new PageList<SmsInfo>();
        pageList.setCurrent(smsInfoReq.getPage());
        pageList.setPageSize(smsInfoReq.getLimit());
        pageList.setDraw(smsInfoReq.getDraw());
        pageList.setTotal(total);
        pageList.setItems(list);
        return pageList;
    }

    @Override
    public PageList<SmsDeliver> listSms(SmsDeliverReq smsInfoReq, Long userId) {
        smsInfoReq.setUserId(userId);
        int total = smsDeliverMapper.countByParams(smsInfoReq);
        //查询
        List<SmsDeliver> list = smsDeliverMapper.selectByParams(smsInfoReq);
        PageList<SmsDeliver> pageList = new PageList<SmsDeliver>();
        pageList.setCurrent(smsInfoReq.getPage());
        pageList.setPageSize(smsInfoReq.getLimit());
        pageList.setDraw(smsInfoReq.getDraw());
        pageList.setTotal(total);
        pageList.setItems(list);
        return pageList;
    }

    @Override
    public int updateSms(Long smsId, SmsStatus status) {
        return smsInfoMapper.updateSmsStatus(smsId, status.getCode());
    }

    @Override
    public PageList<SmsView> listView(SmsViewReq smsViewReq) {
        int total = smsViewMapper.countByParams(smsViewReq);
        List<SmsView> list = smsViewMapper.selectByParams(smsViewReq);
        PageList<SmsView> pageList = new PageList<SmsView>();
        pageList.setCurrent(smsViewReq.getPage());
        pageList.setPageSize(smsViewReq.getLimit());
        pageList.setDraw(smsViewReq.getDraw());
        pageList.setTotal(total);
        pageList.setItems(list);
        return pageList;
    }

}
