package com.hy.lang.mercury.pojo;

import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.utils.DateTimeUtils;
import com.hy.lang.mercury.pojo.enums.*;

import java.math.BigDecimal;
import java.util.Date;

public class SimBase {
    private Long id;

    private Long simId; //SIM NO

    private String iccid;   //iccid

    private String imsi;    //imsi

    private String communication;  //供应商

    private Long suitId;    //套餐ID

    private String suitName;    //套餐名称

    private BigDecimal flowTotal;   //总流量

    private BigDecimal flowUse;     //使用流量

    private BigDecimal flowLess;    //剩余流量

    private Long smsUse;    //使用短信

    private Date limitDate;     //预计到期日期

    private String activateStatus;  //激活 未激活

    private Date activateDate;  //激活日期

    private String overFlow;    //超流量

    private String overSms; //超短信

    private String flagExpire;  //未过期 已过期

    private String flagNearExpire;  //将过期  还早  7天

    private String statusDeliver;       //开机 关机

    private String statusOnline;    //在线 离线

    private String statusArrearage; //欠费 正常

    private Long supplier;  //供应商

    private String status;  //正常 不正常

    private String memo;    //是否已经移除

    private BigDecimal flowUseMonth;//本月消耗流量

    private String openFlag;//开通服务

    private Date openDate;//开通服务时间

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    public SimBase() {

    }

    public SimBase(Long sim, String iccid, String imsi, String communication, String suitName, Long userId) {
        this.simId = sim;
        this.iccid = iccid;
        this.imsi = imsi;
        this.communication = communication;
        this.suitId = 01L;
        this.suitName = suitName;
        this.flowTotal = new BigDecimal(2.0000);
        this.flowUse = new BigDecimal(0.0000);
        this.flowLess = this.flowTotal;
        this.smsUse = 0L;


        this.activateStatus = ActivateStatus.激活.getCode();
        this.activateDate = new Date(); // TODO: 18/4/18
        this.overFlow = OverFlow.未超流量.getCode();
        this.overSms = OverSms.未超短信.getCode();

        //预计到期时间
        this.limitDate = DateTimeUtils.getLimitDate(this.activateDate); // TODO: 18/4/18


        this.flagExpire = FlagExpire.未过期.getCode();
        this.flagNearExpire = FlagExpire.未将过期.getCode();

        this.statusDeliver = StatusDeliver.关机.getCode();
        this.statusOnline = StatusOnline.离线.getCode();
        this.statusArrearage = StatusArrearage.正常.getCode();

        this.supplier = userId;
        this.status = SimStatus.正常.getCode();
        this.createdTime = new Date();
        this.createdBy = Constants.SYS;
        this.updatedTime = new Date();
        this.updatedBy = Constants.SYS;
        this.memo = SimMemo.未移除.getCode();

        this.openFlag = SimOpen.开通.getCode();
        this.openDate = new Date();//todo
        this.flowUseMonth = new BigDecimal(0.0000);

    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public BigDecimal getFlowUseMonth() {
        return flowUseMonth;
    }

    public void setFlowUseMonth(BigDecimal flowUseMonth) {
        this.flowUseMonth = flowUseMonth;
    }


    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public BigDecimal getFlowLess() {
        return flowTotal.subtract(flowUse);
    }

    public void setFlowLess(BigDecimal flowLess) {
        this.flowLess = flowLess;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSimId() {
        return simId;
    }

    public void setSimId(Long simId) {
        this.simId = simId;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication == null ? null : communication.trim();
    }

    public Long getSuitId() {
        return suitId;
    }

    public void setSuitId(Long suitId) {
        this.suitId = suitId;
    }

    public String getSuitName() {
        return suitName;
    }

    public void setSuitName(String suitName) {
        this.suitName = suitName == null ? null : suitName.trim();
    }

    public BigDecimal getFlowTotal() {
        return flowTotal;
    }

    public void setFlowTotal(BigDecimal flowTotal) {
        this.flowTotal = flowTotal;
    }

    public BigDecimal getFlowUse() {
        return flowUse;
    }

    public void setFlowUse(BigDecimal flowUse) {
        this.flowUse = flowUse;
    }

    public Long getSmsUse() {
        return smsUse;
    }

    public void setSmsUse(Long smsUse) {
        this.smsUse = smsUse;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public String getActivateStatus() {
        return activateStatus;
    }

    public void setActivateStatus(String activateStatus) {
        this.activateStatus = activateStatus == null ? null : activateStatus.trim();
    }

    public Date getActivateDate() {
        return activateDate;
    }

    public void setActivateDate(Date activateDate) {
        this.activateDate = activateDate;
    }

    public String getOverFlow() {
        return overFlow;
    }

    public void setOverFlow(String overFlow) {
        this.overFlow = overFlow == null ? null : overFlow.trim();
    }

    public String getOverSms() {
        return overSms;
    }

    public void setOverSms(String overSms) {
        this.overSms = overSms == null ? null : overSms.trim();
    }

    public String getFlagExpire() {
        return flagExpire;
    }

    public void setFlagExpire(String flagExpire) {
        this.flagExpire = flagExpire == null ? null : flagExpire.trim();
    }

    public String getFlagNearExpire() {
        return flagNearExpire;
    }

    public void setFlagNearExpire(String flagNearExpire) {
        this.flagNearExpire = flagNearExpire == null ? null : flagNearExpire.trim();
    }

    public String getStatusDeliver() {
        return statusDeliver;
    }

    public void setStatusDeliver(String statusDeliver) {
        this.statusDeliver = statusDeliver == null ? null : statusDeliver.trim();
    }

    public String getStatusOnline() {
        return statusOnline;
    }

    public void setStatusOnline(String statusOnline) {
        this.statusOnline = statusOnline == null ? null : statusOnline.trim();
    }

    public String getStatusArrearage() {
        return statusArrearage;
    }

    public void setStatusArrearage(String statusArrearage) {
        this.statusArrearage = statusArrearage == null ? null : statusArrearage.trim();
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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