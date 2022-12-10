package com.kang.blog.service.impl;

import com.kang.blog.entity.SysUser;
import com.kang.blog.mapper.SysUserMapper;
import com.kang.blog.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser findUserById(Long authorId) {
        SysUser sysUser = baseMapper.selectById(authorId);
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setNickname("Kang-blog");
        }
        return sysUser;
    }
}
