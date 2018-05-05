package com.hy.lang.mercury.pojo;

import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.pojo.enums.OrderStatus;
import com.hy.lang.mercury.pojo.enums.TransStatus;
import com.hy.lang.mercury.pojo.enums.TransType;
import com.hy.lang.mercury.resource.req.OrderReq;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long id;

    private String name;

    private Long productId;

    private String productName;

    private Long num;

    private Long timeLong;

    private BigDecimal total;

    private BigDecimal price;

    private Long buyer;

    private Long seller;

    private Long status;

    private String transNum;

    private String transPerson;

    private String transPhone;

    private String transStatus;

    private String transAddress;

    private String opUserName;

    private BigDecimal transFee;

    private String memo;

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    public Order(){}

    public Order(OrderReq req) {
        this.name = req.getProductName();
        this.productId = req.getProductId();
        this.num = req.getNum();
        this.timeLong = req.getPeriod();
        this.price = req.getPrice();
        this.num = req.getNum();
        this.total = this.price.multiply(new BigDecimal(this.num));
        this.buyer = Long.valueOf(req.getBuyer());
        this.seller = Constants.SELLER;
        this.transNum = Constants.NVL;
        this.transAddress = req.getTransAddress();
        this.transPhone = req.getTransPhone();
        this.transPerson = req.getTransPerson();
        this.transStatus = TransStatus.代发货.name();
        this.memo = req.getMemo();
        if (TransType.普通.name().equals(req.getTransType())) {
            this.transFee = new BigDecimal(TransType.普通.getFee());
        } else if (TransType.到付.name().equals(req.getTransType())) {
            this.transFee = new BigDecimal(TransType.到付.getFee());
        } else if (TransType.顺丰.name().equals(req.getTransType())) {
            this.transFee = new BigDecimal(TransType.顺丰.getFee());
        } else {
            this.transFee = BigDecimal.ZERO;
        }
        this.status = (Long.valueOf(OrderStatus.已下单.getCode()));
        this.opUserName = req.getBuyer();
        this.createdBy = Constants.SYS;
        this.updatedBy = Constants.SYS;
        this.createdTime = new Date();
        this.updatedTime = new Date();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(Long timeLong) {
        this.timeLong = timeLong;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getBuyer() {
        return buyer;
    }

    public void setBuyer(Long buyer) {
        this.buyer = buyer;
    }

    public Long getSeller() {
        return seller;
    }

    public void setSeller(Long seller) {
        this.seller = seller;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum == null ? null : transNum.trim();
    }

    public String getTransPerson() {
        return transPerson;
    }

    public void setTransPerson(String transPerson) {
        this.transPerson = transPerson == null ? null : transPerson.trim();
    }

    public String getTransPhone() {
        return transPhone;
    }

    public void setTransPhone(String transPhone) {
        this.transPhone = transPhone == null ? null : transPhone.trim();
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public String getTransAddress() {
        return transAddress;
    }

    public void setTransAddress(String transAddress) {
        this.transAddress = transAddress == null ? null : transAddress.trim();
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName == null ? null : opUserName.trim();
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
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