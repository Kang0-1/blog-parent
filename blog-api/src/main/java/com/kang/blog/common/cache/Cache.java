package com.kang.blog.common.cache;

import java.lang.annotation.*;

/**
 * @author 康
 */ //Type 表示注解可以放在类上  Method表示可以放在方法上
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 60 * 1000;

    //缓存标识 key
    String name() default "";

}
