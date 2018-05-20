package com.hy.lang.mercury.resource.req;

import java.io.Serializable;

public class MatrixReq implements Serializable{
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
