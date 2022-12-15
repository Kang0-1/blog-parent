package com.kang.blog.controller;


import com.kang.blog.service.SysUserService;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.params.LoginParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Resource
    private SysUserService userService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams){
        //SSO 单点登录
        return userService.register(loginParams);
    }

}
