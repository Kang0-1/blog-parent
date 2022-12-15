package com.kang.blog.controller;

import com.kang.blog.entity.SysUser;
import com.kang.blog.utils.UserThreadLocal;
import com.kang.blog.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
