package com.hy.lang.mercury.common.entity;

import com.hy.lang.mercury.common.enums.OpRespEnum;

public class ResponseEntity<T> extends BaseSerial {

    private String code;
    private String msg;
    private T data;

    public ResponseEntity() {

    }

    public ResponseEntity(String code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.data = t;
    }

    private ResponseEntity(String code) {
        this.code = code;
    }

    private ResponseEntity(String code, T data) {
        this.code = code;
        this.data = data;
    }

    private ResponseEntity(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.code.equals(OpRespEnum.成功.getCode());
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseEntity<T> createBySuccess() {
        return new ResponseEntity<T>(OpRespEnum.成功.getCode());
    }

    public static <T> ResponseEntity<T> createBySuccessMessage(String msg) {
        return new ResponseEntity<T>(OpRespEnum.成功.getCode(), msg);
    }

    public static <T> ResponseEntity<T> createBySuccess(T data) {
        return new ResponseEntity<T>(OpRespEnum.成功.getCode(), data);
    }

    public static <T> ResponseEntity<T> createBySuccess(String msg, T data) {
        return new ResponseEntity<T>(OpRespEnum.成功.getCode(), msg, data);
    }


    public static <T> ResponseEntity<T> createByError() {
        return new ResponseEntity<T>(OpRespEnum.失败.getCode(), OpRespEnum.失败.name());
    }

    public static <T> ResponseEntity<T> createByErrorMessage(Exception e) {
        return new ResponseEntity<T>(OpRespEnum.失败.getCode(), e.getMessage());
    }

    public static <T> ResponseEntity<T> createByErrorCodeMessage(String errorCode, String errorMessage) {
        return new ResponseEntity<T>(errorCode, errorMessage);
    }
}
