package com.hy.lang.mercury.resource.req;

import com.hy.lang.mercury.common.entity.PageParam;

public class ProductReq extends PageParam {
    private String type;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
