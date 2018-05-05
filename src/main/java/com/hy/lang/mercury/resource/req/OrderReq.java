package com.hy.lang.mercury.resource.req;

import com.hy.lang.mercury.common.entity.PageParam;

import java.math.BigDecimal;

public class OrderReq extends PageParam {

    private Long orderId;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Long num;
    private Long period;
    private String transPerson;
    private String transType;
    private String transPhone;
    private String transAddress;
    private String memo;
    private String buyer;
    private String seller;
    private Long status;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
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

    public String getTransAddress() {
        return transAddress;
    }

    public void setTransAddress(String transAddress) {
        this.transAddress = transAddress;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
}
