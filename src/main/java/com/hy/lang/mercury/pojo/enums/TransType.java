package com.hy.lang.mercury.pojo.enums;

public enum TransType {
    顺丰(23),
    普通(10),
    到付(0);

    private int fee;


    TransType(int i) {
        this.fee = i;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
