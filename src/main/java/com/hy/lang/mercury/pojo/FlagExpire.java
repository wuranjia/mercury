package com.hy.lang.mercury.pojo;

public enum FlagExpire {
    过期("0"), 未过期("1"), 将过期("2"), 未将过期("3");

    private String code;

    private FlagExpire(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
