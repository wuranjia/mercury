package com.hy.lang.mercury.pojo;

import com.hy.lang.mercury.common.Constants;

import java.math.BigDecimal;
import java.util.Date;

public class Store {
    private Long id;

    private Long orderId;

    private Long productId;

    private String storeType;

    private Long cardNum;

    private BigDecimal price;

    private Long buyer;

    private Long seller;

    private BigDecimal total;

    private String transNum;

    private String name;

    private String productType;

    private String flow;

    private String memo;

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    public Store() {
    }

    public Store(Order order, Product product,String storeType) {
        this.orderId = order.getId();
        this.productId = order.getProductId();
        this.storeType = storeType;
        this.cardNum = order.getNum();
        this.price = product.getPrice();
        this.buyer = order.getBuyer();
        this.seller = order.getSeller();
        this.total = order.getTotal();
        this.transNum = order.getTransNum();
        this.name = product.getName();
        this.productType = product.getType();
        this.flow = product.getFlow();
        this.createdBy = Constants.SYS;
        this.updatedBy = Constants.SYS;
        this.createdTime = new Date();
        this.updatedTime = new Date();
        this.memo = Constants.NVL;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType == null ? null : storeType.trim();
    }

    public Long getCardNum() {
        return cardNum;
    }

    public void setCardNum(Long cardNum) {
        this.cardNum = cardNum;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum == null ? null : transNum.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
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