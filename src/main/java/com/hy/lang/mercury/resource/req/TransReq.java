package com.hy.lang.mercury.resource.req;

import com.hy.lang.mercury.common.entity.PageParam;

import java.math.BigDecimal;

public class TransReq extends PageParam {

    private Long orderId;

    private String transNum;

    private String transAddress;

    private BigDecimal transFee;

    private String transStatus;

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getTransAddress() {
        return transAddress;
    }

    public void setTransAddress(String transAddress) {
        this.transAddress = transAddress;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
