package com.kang.blog.admin.service;

import com.kang.blog.admin.pojo.Admin;
import com.kang.blog.admin.pojo.Permission;

import java.util.List;

public interface AdminService {
    Admin findAdminByUsername(String username);

    List<Permission> findPermissionByAdminId(Long adminId);
}
