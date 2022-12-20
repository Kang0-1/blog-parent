package com.kang.blog.common.aop;

import java.lang.annotation.*;

//Type 表示注解可以放在类上  Method表示可以放在方法上
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";

}
