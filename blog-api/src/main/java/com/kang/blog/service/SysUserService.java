package com.kang.blog.service;

import com.kang.blog.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.UserVo;
import com.kang.blog.vo.params.LoginParams;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@Transactional
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过id查询用户
     * @param authorId
     * @return
     */
    SysUser findUserById(Long authorId);

    /**
     * 根据账号、密码返回用户信息（登录时使用）
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 登录成功后通过检测token，并返回用户信息到前端展示
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 注册，成功后返回token
     *
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);

    /**
     * 通过id查询用户并封装成UserVo返回
     *
     * @param authorId
     * @return
     */
    UserVo findUserVoById(Long authorId);
}
