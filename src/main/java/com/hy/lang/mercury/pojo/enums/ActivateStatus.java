package com.hy.lang.mercury.pojo.enums;

public enum ActivateStatus {
    激活("1"), 未激活("0");

    private String code;

    private ActivateStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    }
