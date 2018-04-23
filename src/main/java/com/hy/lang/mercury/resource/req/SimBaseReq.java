package com.hy.lang.mercury.resource.req;

import com.hy.lang.mercury.common.entity.PageParam;

import java.util.List;

public class SimBaseReq extends PageParam {

    private Long userId;

    private List<Long> userIds;

    private Long sim;

    private String iccid;

    private Long supplier;

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public Long getSim() {
        return sim;
    }

    public void setSim(Long sim) {
        this.sim = sim;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
