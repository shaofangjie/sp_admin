package com.sp.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.sp.admin.commonutil.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;

import java.util.List;

public class BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected RedisUtil redisUtil;

    public JSONObject getViolationErrMsg(List<FieldError> detailErr) {
        JSONObject error = new JSONObject();
        detailErr.forEach(fieldError -> {
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return error;
    }


}
