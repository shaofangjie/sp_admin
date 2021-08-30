package com.sp.admin.exception;

import com.sp.admin.commonutil.response.ResponseCode;

/**
 * 更新重试异常
 */
public class TryAgainException extends ApiException {

    public TryAgainException(ResponseCode apiResultEnum) {
        super(apiResultEnum);
    }

}
