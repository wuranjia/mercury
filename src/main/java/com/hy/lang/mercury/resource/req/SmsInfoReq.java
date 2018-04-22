package com.hy.lang.mercury.resource.req;

import com.hy.lang.mercury.client.ws.SmsStatus;
import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.entity.PageParam;

public class SmsInfoReq extends PageParam {
    private Long smsId;

    private Long userId;

    private String receiveInfo;

    private String smsContent;

    private String smsOther;

    private Integer status;

    private String memo;

    public SmsInfoReq() {

    }

    public SmsInfoReq(SmsStatus smsStatus) {
        this.status = smsStatus.getCode();
        this.limit = Constants.NUM3000_INT;
        this.page = Constants.ONE_INT;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReceiveInfo() {
        return receiveInfo;
    }

    public void setReceiveInfo(String receiveInfo) {
        this.receiveInfo = receiveInfo;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getSmsOther() {
        return smsOther;
    }

    public void setSmsOther(String smsOther) {
        this.smsOther = smsOther;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
