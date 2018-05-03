package com.hy.lang.mercury.pojo.enums;

public enum StatusOnline {
    在线("1"), 离线("0");

    private String code;

    private StatusOnline(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    }
