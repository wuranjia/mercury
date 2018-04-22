package com.hy.lang.mercury.pojo;

public enum SimOpen {
    开通("1"), 未开通("0");

    private String code;

    private SimOpen(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
