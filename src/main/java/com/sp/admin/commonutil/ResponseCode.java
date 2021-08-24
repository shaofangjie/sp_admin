package com.sp.admin.commonutil;

public enum ResponseCode {
    SUCCESS(200,"成功"),
    ERROR(1,"错误"),
    NEED_REGISTER(10,"需要注册,请授权登录!"),
    NEED_LOGIN(12,"需要登录,请登录!"),

    TOMANYLOGIN(11,"账号被挤出."),
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