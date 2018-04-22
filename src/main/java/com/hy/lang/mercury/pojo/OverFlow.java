package com.hy.lang.mercury.pojo;

public enum OverFlow {
    未超流量("1"), 超流量("0");

    private String code;

    private OverFlow(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    }
