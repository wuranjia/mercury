package com.hy.lang.mercury.service;

import com.hy.lang.mercury.client.ws.SmsStatus;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.pojo.SmsDeliver;
import com.hy.lang.mercury.pojo.SmsInfo;
import com.hy.lang.mercury.pojo.SmsView;
import com.hy.lang.mercury.resource.req.MatrixReq;
import com.hy.lang.mercury.resource.req.SmsDeliverReq;
import com.hy.lang.mercury.resource.req.SmsInfoReq;
import com.hy.lang.mercury.resource.req.SmsViewReq;
import com.hy.lang.mercury.resource.resp.MatrixResp;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface SmsAble {

    int sendSms(String receiveIds, String smsContent, Long userId);

    PageList<SmsInfo> listSms(SmsInfoReq smsInfoReq, Long userId);

    public PageList<SmsDeliver> listSms(SmsDeliverReq smsInfoReq, Long userId);

    int updateSms(Long smsId, SmsStatus status);

    PageList<SmsView> listView(SmsViewReq smsViewReq);

    Map<String,String[]> matrix(MatrixReq req);
}
