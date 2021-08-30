package com.sp.admin.exception;


import com.sp.admin.commonutil.response.ResponseCode;
import lombok.Data;

/**
 * 自定义的api异常
 */
@Data
public class ApiException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;
	private Object data;
	private Exception exception;
	public ApiException() {
		super();
	}

	public ApiException(String message) {
		super(message);
		this.message=message;
	}

	public ApiException(int status, String message, Object data, Exception exception) {
		super(message,exception);
		this.status = status;
		this.message = message;
		this.data = data;
		this.exception = exception;
	}
	public ApiException(ResponseCode apiResultEnum) {
		this(apiResultEnum.getCode(),apiResultEnum.getDesc(),null,null);
	}
	public ApiException(ResponseCode apiResultEnum, Object data) {
		this(apiResultEnum.getCode(),apiResultEnum.getDesc(),data,null);
	}
	public ApiException(ResponseCode apiResultEnum, Object data, Exception exception) {
		this(apiResultEnum.getCode(),apiResultEnum.getDesc(),data,exception);
	}


}
