package com.sp.admin.aspect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 重试注解
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IsTryAgain {
}
