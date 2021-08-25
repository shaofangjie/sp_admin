package com.sp.admin.commonutil;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class ServerResponse<T> implements Serializable {

    private final int status;
    private String msg;
    private int detail;
    private T data;//可以指定泛型里面的内容，也可以不指定，而且里面的类型可以是多种，map,list,string

    //编写 私有 的构造方法，外部是不能new的
    // 开放供外部使用的Public方法
    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, int detail, T data) {
        this.status = status;
        this.msg = msg;
        this.detail = detail;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    //编写成功静态的方法供外部的调用
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);

    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);

    }

    //编写失败的方法
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorcode, String erroeMessage) {
        return new ServerResponse<T>(errorcode, erroeMessage);
    }

    public static <T> ServerResponse<T> createByErrorMessage(String msg, int detail, T data) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), msg, detail, data);
    }

    public static <T> ServerResponse<T> createByErrorNeeDLogin(String erroeMessage) {
        return new ServerResponse<T>(ResponseCode.NEED_REGISTER.getCode(), erroeMessage);
    }


    //编写外部访问的Public方法,之前需要写一个枚举类
    //这样外部的显示的就是这几个值啦
    public int getStatus() {
        return status;
    }

    public int getDetail() {
        return detail;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    //判断是否登陆成功
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

}
