package com.hy.lang.mercury.pojo.enums;

public enum OrderStatus {
    已下单(10),
    已支付(20),
    已发货(30),
    已收货(40),
    已取消(90);

    private int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
