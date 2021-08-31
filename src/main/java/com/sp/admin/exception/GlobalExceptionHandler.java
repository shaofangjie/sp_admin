package com.sp.admin.exception;

import com.alibaba.fastjson.JSONObject;
import com.sp.admin.commonutil.response.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局表单校验处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数校验错误异常
     *
     * @param e MethodArgumentNotValidException|BindException
     * @return ResponseData
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public ServerResponse bindExceptionHandler(Exception e) {
        BindingResult bindingResult;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        } else {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }

        JSONObject errors = new JSONObject();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ServerResponse.createByErrorMessage("参数错误", 1, errors);
    }

}