package com.hy.lang.mercury.client.ws;

public enum SmsStatus {

    发送中(1),
    发送成功(2),
    发送失败(3),
    接收成功(4);

    private int code;

    SmsStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
