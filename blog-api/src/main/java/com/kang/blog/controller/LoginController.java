package com.kang.blog.controller;

import com.kang.blog.service.LoginService;
import com.kang.blog.service.SysUserService;
import com.kang.blog.vo.Result;
import com.kang.blog.vo.params.LoginParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.login(loginParams);
    }

}
