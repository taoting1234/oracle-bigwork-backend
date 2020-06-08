package com.newitd.oracle.base;

public class ApiException extends RuntimeException {

    private Integer code;
    private String msg;

    public ApiException(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public ApiException(String msg) {
        this.msg = msg;
        this.code = -999;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
