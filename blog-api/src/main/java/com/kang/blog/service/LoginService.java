package com.kang.blog.service;

import com.kang.blog.entity.SysUser;
import com.kang.blog.vo.Result;
import com.kang.blog.vo.params.LoginParams;

public interface LoginService {
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);
}
