package com.sp.admin.commonutil;

public enum ResponseCode {

    CAPTCHA_FAILED(0, "验证码错误"),
    USERNAME_PWD_FAILED(0, "用户名或密码错误"),
    LOGIN_FAILED(0, "登录失败,请重试."),
    USER_DISABLE(0, "账号已禁用"),
    LOGIN_SUCCESS(1, "登录成功"),

    SUCCESS(200,"成功"),
    ERROR(1,"错误请求"),
    NEED_REGISTER(10,"请注册后登录!"),
    NEED_LOGIN(12,"请登录!"),

    TOMANYLOGIN(11,"您的账号账号被挤出."),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc){
        this.code=code;
        this.desc=desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}