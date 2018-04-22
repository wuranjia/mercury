package com.hy.lang.mercury.pojo;

public enum StatusDeliver {
    开机("1"), 关机("0");

    private String code;

    private StatusDeliver(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    }
