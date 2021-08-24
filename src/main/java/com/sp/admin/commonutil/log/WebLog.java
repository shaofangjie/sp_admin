package com.sp.admin.commonutil.log;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {
    /** 日志描述信息 */
    String description() default "";
}
