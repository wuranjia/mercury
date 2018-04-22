package com.hy.lang.mercury.pojo;

public enum SimMemo {
    移除("0"),未移除("1");

    private String code;

    private SimMemo(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
