package com.kang.blog.controller;


import com.kang.blog.entity.SysUser;
import com.kang.blog.service.SysUserService;
import com.kang.blog.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@RestController
@RequestMapping("users")
public class SysUserController {

    @Resource
    private SysUserService userService;

    @PostMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
//        public Result currentUser(HttpServletRequest request){
//        String token = request.getHeader("Authorization");}

        return userService.findUserByToken(token);
    }

}

