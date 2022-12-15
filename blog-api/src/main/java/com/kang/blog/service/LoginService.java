package com.kang.blog.service;

import com.kang.blog.entity.SysUser;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.params.LoginParams;

public interface LoginService {
    /**
     * 登录接口
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);
}
