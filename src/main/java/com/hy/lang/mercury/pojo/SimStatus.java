package com.hy.lang.mercury.pojo;

public enum SimStatus {
    正常("1"), 不正常("0");

    private String code;

    private SimStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    }
