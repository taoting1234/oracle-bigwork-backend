package com.newitd.oracle.base;


public class Result {

    private Integer code;

    private String message;


    public static Result error(Integer code, String message) {
        Result result = new Result();
        result.setMessage(message);
        result.setCode(code);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
