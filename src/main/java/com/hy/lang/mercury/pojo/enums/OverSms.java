package com.hy.lang.mercury.pojo.enums;

public enum OverSms {
    未超短信("1"), 超短信("0");

    private String code;

    private OverSms(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    }
