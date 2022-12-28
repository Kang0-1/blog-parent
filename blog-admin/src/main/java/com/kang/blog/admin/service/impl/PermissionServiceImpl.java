package com.kang.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.blog.admin.mapper.PermissionMapper;
import com.kang.blog.admin.pojo.Permission;
import com.kang.blog.admin.service.PermissionService;
import com.kang.blog.admin.vo.Result;
import com.kang.blog.admin.vo.params.PageParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public Result listPermission(PageParams pageParams) {
        Page<Permission> page = new Page<>(pageParams.getCurrentPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParams.getQueryString())) {
            //queryWrapper.eq(Permission::getName,pageParams.getQueryString());
            queryWrapper.like(Permission::getName, pageParams.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("list", permissionPage.getRecords());
        map.put("total", permissionPage.getTotal());
        return Result.success(map);
    }

    @Override
    public Result add(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result update(Permission permission) {
        permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result delete(Long id) {
        permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
