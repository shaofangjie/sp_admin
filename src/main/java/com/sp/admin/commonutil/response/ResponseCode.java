package com.sp.admin.commonutil.response;

public enum ResponseCode {

    CAPTCHA_FAILED(0, "验证码错误"),
    USERNAME_PWD_FAILED(0, "用户名或密码错误"),
    LOGIN_FAILED(0, "登录失败,请重试."),
    USER_DISABLE(0, "账号已禁用"),
    LOGIN_SUCCESS(1, "登录成功"),

    USER_ADD_FAILED(0, "添加失败"),
    USER_ADD_SUCCESS(1, "添加成功"),
    USER_EXIST(0, "用户已存在"),
    USER_ROLE_NOT_EXIST(0, "角色不存在"),

    USER_EDIT_FAILED(0, "修改失败"),
    USER_EDIT_SUCCESS(1, "修改成功"),
    USER_NOT_EXIST(0, "用户不存在"),
    USER_CANT_EDIT(0, "此用户无法编辑"),

    USER_DEL_FAILED(0, "删除失败"),
    USER_DEL_SUCCESS(1, "删除成功"),
    USER_CANT_DEL(0, "此用户无法删除"),

    ROLE_ADD_FAILED(0, "角色添加失败"),
    ROLE_NAME_EXIST(0, "角色名已存在"),
    ROLE_ADD_SUCCESS(1, "添加成功"),
    ROLE_RESOURCE_IS_ERROR(0, "角色权限错误"),

    ROLE_EDIT_FAILED(0, "角色修改失败"),
    ROLE_EDIT_SUCCESS(1, "角色修改成功"),
    ROLE_NOT_EXIST(0, "角色不存在"),
    ROLE_CANT_EDIT(0, "角色权限错误"),

    ROLE_DEL_FAILED(0, "角色删除失败"),
    ROLE_DEL_SUCCESS(1, "角色删除成功"),
    ROLE_CANT_DEL(0, "此角色无法删除"),

    ERROR_TRY_AGAIN(506,"正在重试"),
    ERROR_TRY_AGAIN_FAILED(507,"重试失败"),

    SUCCESS(200,"成功"),
    ERROR(1,"错误请求"),
    NEED_REGISTER(10,"请注册后登录!"),
    NEED_LOGIN(12,"请登录!"),

    TOMANYLOGIN(11,"您的账号账号被挤出."),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private int code = 0;
    private String desc = null;

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