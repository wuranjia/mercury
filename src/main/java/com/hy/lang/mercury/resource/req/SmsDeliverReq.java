package com.hy.lang.mercury.resource.req;

import com.hy.lang.mercury.common.entity.PageParam;

public class SmsDeliverReq extends PageParam {

    private Long userId;

    private String msgId;

    private String dstId;

    private String smsContent;

    private String serviceId;

    private String srcTermid;


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getDstId() {
        return dstId;
    }

    public void setDstId(String dstId) {
        this.dstId = dstId;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSrcTermid() {
        return srcTermid;
    }

    public void setSrcTermid(String srcTermid) {
        this.srcTermid = srcTermid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
