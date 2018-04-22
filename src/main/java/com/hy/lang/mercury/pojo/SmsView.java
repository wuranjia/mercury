package com.hy.lang.mercury.pojo;

import com.hy.lang.mercury.common.Constants;

import java.util.Date;

public class SmsView {
    private Long id;

    private Long smsId;

    private String smsContent;

    private String serviceId;

    private String simNo;

    private String memo;//状态

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    public SmsView() {

    }

    public SmsView(Long smsId, String dest, String smsContent, SmsViewType type, String status) {
        this.smsId = smsId;
        this.simNo = dest;
        this.smsContent = smsContent;
        this.serviceId = type.name();
        this.memo = status;
        this.createdTime = new Date();
        this.createdBy = Constants.NVL;
        this.updatedTime = new Date();
        this.updatedBy = Constants.NVL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo == null ? null : simNo.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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
        this.createdBy = createdBy == null ? null : createdBy.trim();
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
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }
}