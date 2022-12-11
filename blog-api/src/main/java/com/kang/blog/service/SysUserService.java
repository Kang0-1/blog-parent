package com.kang.blog.service;

import com.kang.blog.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.blog.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
public interface SysUserService extends IService<SysUser> {

    SysUser findUserById(Long authorId);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);
}
