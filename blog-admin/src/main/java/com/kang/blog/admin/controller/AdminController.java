package com.kang.blog.admin.controller;


import com.kang.blog.admin.pojo.Permission;
import com.kang.blog.admin.service.PermissionService;
import com.kang.blog.admin.vo.Result;
import com.kang.blog.admin.vo.params.PageParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParams pageParams) {
        return permissionService.listPermission(pageParams);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission) {
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission) {
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return permissionService.delete(id);
    }


}
