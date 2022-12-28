package com.kang.blog.admin.service;

import com.kang.blog.admin.pojo.Admin;
import com.kang.blog.admin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class authService {

    @Resource
    private AdminService adminService;

    /**
     * 权限认证
     *
     * @param request
     * @param authentication
     * @return
     */
    public Boolean auth(HttpServletRequest request, Authentication authentication) {
        //请求路径
        String requestURI = request.getRequestURI();
        requestURI = StringUtils.split(requestURI, '?')[0];
        //用户信息，UserDetail
        Object principal = authentication.getPrincipal();
        if (null == principal || "anonymousUser".equals(principal)) {
            //未登录
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUsername(username);
        if (null == admin) {
            return false;
        }
        if (1 == admin.getId()) {
            //超级管理员
            return true;
        }
        List<Permission> permissionList = adminService.findPermissionByAdminId(admin.getId());
        for (Permission permission : permissionList) {
            if (requestURI.equals(permission.getPath())) {
                return true;
            }
        }
        return false;

    }

}
