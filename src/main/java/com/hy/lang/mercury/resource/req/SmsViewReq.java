package com.hy.lang.mercury.resource.req;

import com.hy.lang.mercury.common.entity.PageParam;

public class SmsViewReq extends PageParam {

    private Long userId;


    private String sim;


    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
