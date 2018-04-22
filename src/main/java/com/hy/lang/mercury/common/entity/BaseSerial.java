package com.hy.lang.mercury.common.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class BaseSerial implements Serializable {

    private static final long serialVersionUID = -2429154493096308887L;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
