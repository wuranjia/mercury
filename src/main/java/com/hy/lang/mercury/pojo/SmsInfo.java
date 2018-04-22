package com.hy.lang.mercury.pojo;

import com.hy.lang.mercury.client.ws.SmsStatus;
import com.hy.lang.mercury.common.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class SmsInfo {
    private Long smsId;

    private Long userId;

    private String receiveInfo;

    private String smsContent;

    private String smsOther;

    private Integer status;

    private String memo;

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    public SmsInfo() {
    }

    public SmsInfo(String receiveInfo, String smsContent, Long userId, String requestId) {
        this.userId = userId;
        this.receiveInfo = receiveInfo;//StringUtils.join(receivedArray, Constants.分号);
        this.smsContent = smsContent;
        this.smsOther = StringUtils.isBlank(requestId) ? Constants.NVL : requestId;
        this.memo = Constants.NVL;
        this.status = SmsStatus.发送中.getCode();
        this.createdTime = new Date();
        this.createdBy = Constants.NVL;
        this.updatedTime = new Date();
        this.updatedBy = Constants.NVL;
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
        this.receiveInfo = receiveInfo == null ? null : receiveInfo;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent;
    }

    public String getSmsOther() {
        return smsOther;
    }

    public void setSmsOther(String smsOther) {
        this.smsOther = smsOther == null ? null : smsOther;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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