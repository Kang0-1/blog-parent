package com.kang.blog.admin.service;

import com.kang.blog.admin.pojo.Permission;
import com.kang.blog.admin.vo.Result;
import com.kang.blog.admin.vo.params.PageParams;

public interface PermissionService {
    Result listPermission(PageParams pageParams);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
