package com.hy.lang.mercury.common;

import com.hy.lang.mercury.common.enums.OpRespEnum;

public class McException extends Exception {
    public McException(OpRespEnum opRespEnum) {
        super(opRespEnum.name());
    }

    public McException(String msg){
        super(msg);
    }
}
