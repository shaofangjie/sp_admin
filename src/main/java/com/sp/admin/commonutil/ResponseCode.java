package com.sp.admin.commonutil;

public enum ResponseCode {
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