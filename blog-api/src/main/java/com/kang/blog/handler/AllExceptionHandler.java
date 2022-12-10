package com.kang.blog.handler;

import com.kang.blog.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//对加了 @controller 注解的方法进行拦截处理  （AOP的实现）
@RestControllerAdvice
public class AllExceptionHandler {

    //进行异常处理，处理 Exception.class 的异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception exception){
        exception.printStackTrace();
        return Result.fail(-999,"系统异常,请联系开发人员");
    }

}
