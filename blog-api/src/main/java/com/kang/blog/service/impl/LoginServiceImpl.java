package com.kang.blog.service.impl;

import com.kang.blog.service.LoginService;
import com.kang.blog.service.SysUserService;
import com.kang.blog.vo.Result;
import com.kang.blog.vo.params.LoginParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private SysUserService sysUserService;

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1.检查参数是否合法
         * 2.查询用户名对应用户是否在数据库存在
         * 3.如果不存在，登录失败
         * 4.如果存在，使用jwt生成token，返回前端
         * 5.token存放到redis中， token：user  设置过期时间
         */

        return null;
    }
}
