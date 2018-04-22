package com.hy.lang.mercury.pojo;

import com.hy.lang.mercury.common.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class User {
    private Long userId;

    private Long parentId;

    private String userName;

    private String loginPwd;

    private String payPwd;

    private String phone;

    private String company;

    private String address;

    private String memo;

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    public User() {
    }

    public User(@NotNull String userName, @NotNull String loginPwd, @NotNull String trdPwd, String phone, String company, String address, String memo, String parentId) {
        this.userName = userName;
        this.loginPwd = loginPwd;
        this.payPwd = trdPwd;
        this.phone = phone;
        this.company = company;
        this.address = address;
        this.memo = memo;
        this.parentId = StringUtils.isBlank(parentId) ? -1 : Long.valueOf(parentId);

        this.createdBy = Constants.SYS;
        this.createdTime = new Date();
        this.updatedBy = Constants.SYS;
        this.updatedTime = new Date();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd == null ? null : payPwd.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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