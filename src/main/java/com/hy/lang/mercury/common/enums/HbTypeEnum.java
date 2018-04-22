package com.hy.lang.mercury.common.enums;

import com.hy.lang.mercury.common.Constants;

public enum HbTypeEnum {
    登入(Constants.ZERO_INT),
    登出(Constants.ONE_INT);

    private int code;

    HbTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
