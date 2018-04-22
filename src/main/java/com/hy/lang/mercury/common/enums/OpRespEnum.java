package com.hy.lang.mercury.common.enums;

public enum OpRespEnum {

    成功("0000"),
    用户不存在("1001"),
    密码不正确("1002"),
    SESSION失效_需要登录("1003"),
    失败("9999"), 用户已存在("1004"), 长连接建立失败("8001"), 长连接建立失败_HOST("8002");

    private String code;

    OpRespEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
