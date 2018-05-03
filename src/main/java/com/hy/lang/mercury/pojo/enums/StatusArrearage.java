package com.hy.lang.mercury.pojo.enums;

public enum StatusArrearage {
    正常("1"), 欠费("0"), 未知("-1");

    private String code;

    private StatusArrearage(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
