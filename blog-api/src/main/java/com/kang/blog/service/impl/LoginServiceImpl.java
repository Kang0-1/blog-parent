package com.kang.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.kang.blog.entity.SysUser;
import com.kang.blog.service.LoginService;
import com.kang.blog.service.SysUserService;
import com.kang.blog.utils.JWT_Utils;
import com.kang.blog.utils.MD5_Utils;
import com.kang.blog.utils.ErrorCode;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.params.LoginParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {


    @Resource
    private SysUserService sysUserService;


    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1.检查参数是否合法
         * 2.查询用户名对应用户是否在数据库存在
         * 3.如果不存在，登录失败
         * 4.如果存在，使用jwt生成token，返回前端
         * 5.token存放到redis中， token：user  设置过期时间
         */
        String account=loginParams.getAccount();
        String password=loginParams.getPassword();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser= sysUserService.findUser(account, MD5_Utils.md5Lower(password,MD5_Utils.saltValue));
        if(sysUser==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWT_Utils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> map = JWT_Utils.checkToken(token);
        if(null==map){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }
        return JSON.parseObject(userJson,SysUser.class);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
}
