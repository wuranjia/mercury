package com.hy.lang.mercury.pojo;

import java.util.Date;

public class SmsDeliver {
    private Long smsId;

    private String msgId;

    private String dstId;

    private String smsContent;

    private String serviceId;

    private String srcTermid;

    private String srcTermtype;

    private String memo;

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

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
        this.dstId = dstId == null ? null : dstId;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId;
    }

    public String getSrcTermid() {
        return srcTermid;
    }

    public void setSrcTermid(String srcTermid) {
        this.srcTermid = srcTermid == null ? null : srcTermid;
    }

    public String getSrcTermtype() {
        return srcTermtype;
    }

    public void setSrcTermtype(String srcTermtype) {
        this.srcTermtype = srcTermtype == null ? null : srcTermtype;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy;
    }
}