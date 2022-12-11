package com.kang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kang.blog.entity.SysUser;
import com.kang.blog.mapper.SysUserMapper;
import com.kang.blog.service.LoginService;
import com.kang.blog.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.blog.vo.ErrorCode;
import com.kang.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private LoginService loginService;


    @Override
    public SysUser findUserById(Long authorId) {
        SysUser sysUser = baseMapper.selectById(authorId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("Kang-blog");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {

        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, account).eq(SysUser::getPassword, password).
                select(SysUser::getAccount, SysUser::getAvatar, SysUser::getId, SysUser::getNickname));
    }

    /**
     * 从 token 中获取用户信息
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {

        SysUser user=loginService.checkToken(token);
        if(null==user){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        return null;
    }
}
