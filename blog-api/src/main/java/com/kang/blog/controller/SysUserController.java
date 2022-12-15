package com.kang.blog.controller;


import com.kang.blog.service.SysUserService;
import com.kang.blog.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
//        public Result currentUser(HttpServletRequest request){
//        String token = request.getHeader("Authorization");}

        return userService.findUserByToken(token);
    }

}

