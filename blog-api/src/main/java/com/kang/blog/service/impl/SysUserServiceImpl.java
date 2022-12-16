package com.kang.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kang.blog.entity.SysUser;
import com.kang.blog.mapper.SysUserMapper;
import com.kang.blog.service.LoginService;
import com.kang.blog.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.blog.utils.JWT_Utils;
import com.kang.blog.utils.MD5_Utils;
import com.kang.blog.utils.ErrorCode;
import com.kang.blog.vo.LoginUserVo;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.UserVo;
import com.kang.blog.vo.params.LoginParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedisTemplate<String,String> redisTemplate;

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

        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getAccount, account)
                .eq(SysUser::getPassword, password)
                .select(SysUser::getId, SysUser::getAccount, SysUser::getNickname, SysUser::getAvatar));
    }


    @Override
    public Result findUserByToken(String token) {

        SysUser user=loginService.checkToken(token);
        if(null==user){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo=new LoginUserVo();
        BeanUtils.copyProperties(user,loginUserVo);
        return Result.success(loginUserVo);
    }

    @Override
    public Result register(LoginParams loginParams) {
        /**
         * 1.判断参数合法性
         * 2.判断账号是否存在，存在->返回账号被注册信息
         * 3.不存在账号->注册用户
         * 4.生成token，存到redis
         * 5。注意，要加上事务，上面的注册流程如果有问题，将回滚
         */
        if(StringUtils.isBlank(loginParams.getAccount())
                ||StringUtils.isBlank(loginParams.getPassword())
                ||StringUtils.isBlank(loginParams.getNickname())){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        SysUser sysUser = baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, loginParams.getAccount()).
                                                last("limit 1"));
        if(sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        SysUser user=new SysUser();
        user.setNickname(loginParams.getNickname());
        user.setAccount(loginParams.getAccount());
        user.setPassword(MD5_Utils.md5Lower(loginParams.getPassword(), MD5_Utils.saltValue));
        user.setCreateDate(System.currentTimeMillis());
        user.setLastLogin(System.currentTimeMillis());
        baseMapper.insert(user);

        String token = JWT_Utils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser user = baseMapper.selectById(authorId);
        if (user == null) {
            user = new SysUser();
            user.setId(1L);
            user.setAvatar("/static/img/default_avatar.png");
            user.setNickname("Kang-blog");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }
}
