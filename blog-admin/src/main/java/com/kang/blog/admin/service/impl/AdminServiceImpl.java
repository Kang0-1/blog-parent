package com.kang.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kang.blog.admin.mapper.AdminMapper;
import com.kang.blog.admin.pojo.Admin;
import com.kang.blog.admin.pojo.Permission;
import com.kang.blog.admin.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    public Admin findAdminByUsername(String username) {
        return adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, username).last("limit 1"));
    }

    @Override
    public List<Permission> findPermissionByAdminId(Long adminId) {
        return adminMapper.findPermissionByAdminId(adminId);
    }

}
