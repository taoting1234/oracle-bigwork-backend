package com.newitd.oracle.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public Result handle(ApiException e) {
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMessage(e.getMsg());
        return result;
    }
}
