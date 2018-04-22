package com.hy.lang.mercury.resource.req;

import com.hy.lang.mercury.common.entity.PageParam;

public class SimBaseReq extends PageParam {

    private Long userId;


    private Long sim;

    private String iccid;

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
