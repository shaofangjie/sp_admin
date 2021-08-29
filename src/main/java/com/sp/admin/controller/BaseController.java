package com.sp.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.sp.admin.commonutil.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.validation.FieldError;

import java.util.List;

public class BaseController {

    @Autowired
    protected RedisUtil redisUtil;
    @Autowired
    protected ApplicationContext applicationContext;

    public boolean isDev(){
        Environment env = applicationContext.getEnvironment();
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("dev".equals(profile)) {
                return true;
            }
        }
        return false;
    }

    public JSONObject getViolationErrMsg(List<FieldError> detailErr) {
        JSONObject error = new JSONObject();
        detailErr.forEach(fieldError -> {
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return error;
    }

    public JSONObject buildTableResult(int code, String msg, long count, List<Object> pageData) {

        JSONObject result = new JSONObject();
        result.put("data", pageData);
        result.put("count", count);
        result.put("code", code);
        result.put("msg", msg);

        return result;

    }

}
