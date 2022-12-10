package com.kang.blog.service;

import com.kang.blog.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
