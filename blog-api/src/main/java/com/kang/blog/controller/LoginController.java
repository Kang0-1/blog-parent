package com.kang.blog.controller;

import com.kang.blog.service.LoginService;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.params.LoginParams;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("logout")
    public Result currentUser(@RequestHeader("Authorization") String token) {
//        public Result currentUser(HttpServletRequest request){
//        String token = request.getHeader("Authorization");}

        return loginService.logout(token);
    }

}
