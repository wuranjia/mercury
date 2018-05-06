package com.hy.lang.mercury.pojo;

import com.hy.lang.mercury.common.Constants;

import java.util.Date;

public class StoreDetail implements Cloneable{
    private Long id;

    private Long storeId;

    private Long orderId;

    private String simId;

    private String iccid;

    private String imsi;

    private String memo;

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    public StoreDetail() {
    }

    public StoreDetail(Long sim, String iccid, String imsi, Long orderId, Long storeId) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.simId = sim+"";
        this.iccid = iccid;
        this.imsi = imsi;
        this.createdBy = Constants.SYS;
        this.updatedBy = Constants.SYS;
        this.createdTime = new Date();
        this.updatedTime = new Date();
        this.memo = Constants.NVL;    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
        this.createdBy = createdBy;
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
        this.updatedBy = updatedBy;
    }

    @Override
    public StoreDetail clone() {
        StoreDetail sc = null;
        try {
            sc = (StoreDetail) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return sc;
    }
}