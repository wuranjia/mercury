package com.hy.lang.mercury.pojo.enums;

public enum OrderStatus {
    已下单(10L),
    已支付(20L),
    已发货(30L),
    已收货(40L),
    已取消(90L);

    private Long code;

    OrderStatus(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
