package com.kang.blog.handler;


import com.alibaba.fastjson.JSON;
import com.kang.blog.entity.SysUser;
import com.kang.blog.service.LoginService;
import com.kang.blog.utils.UserThreadLocal;
import com.kang.blog.utils.ErrorCode;
import com.kang.blog.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


     @Autowired
     private LoginService loginService;
//    private LoginService loginService=new LoginServiceImpl();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //preHandle 在执行controller方法之前执行
        /**
         * 1. 判断 请求的接口路径 是否为 HandlerMethod (controller方法)
         * 2. 判断 token 是否为空(登录)
         * 3. 验证token
         * 4.认证成功，放行
         */
        if(!(handler instanceof HandlerMethod)){  // 不是访问controller方法，则放行 handler 可能是 资源handler(RequestResourceHandler)
            return true;
        }

        String token = request.getHeader("Authorization");  //从请求中获取 token

        log.info("==========================request start===============================");
        log.info("request URI:{}",request.getRequestURI());
        log.info("request method:{}",request.getMethod());
        log.info("token:{}",token);
        log.info("==========================request end==  =============================");


        SysUser user = loginService.checkToken(token); // checkToken(token) 先判断 token 是否为空 ，再验证redis中是否存有该token
        if(null==user){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        UserThreadLocal.put(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
