package com.hy.lang.mercury.resource.req;

import com.hy.lang.mercury.common.entity.PageParam;

public class TransReq extends PageParam {

    private Long orderId;

    private String transNum;

    private String transAddress;

    private String transPerson;

    private String transPhone;

    private String transStatus;

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getTransPerson() {
        return transPerson;
    }

    public void setTransPerson(String transPerson) {
        this.transPerson = transPerson;
    }

    public String getTransPhone() {
        return transPhone;
    }

    public void setTransPhone(String transPhone) {
        this.transPhone = transPhone;
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
