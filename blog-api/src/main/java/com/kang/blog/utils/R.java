package com.kang.blog.utils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class R {

    private Boolean success;
    private Integer code;
    private String msg;
    private Map<String ,Object> data=new HashMap<>();

    //把构造方法私有
    private R(){}

    //成功静态方法
    public static R success(){
        R r =new R();
        r.setSuccess(true);
        r.setCode(200);
        r.setMsg("success!");
        return r;
    }

    //失败静态方法
    public static R error(){
        R r =new R();
        r.setSuccess(false);
        r.setCode(400);
        r.setMsg("error!");
        return r;
    }

    //附加在success()和error()后面的
    //如  return Result.success().success(true).code(200).msg("成功").data("key","value");

    public R success(Boolean isSuccess){
        this.setSuccess(isSuccess);
        return this;
    }

    public R code(int code){
        this.setCode(code);
        return this;
    }

    public R msg(String message){
        this.setMsg(message);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key,value);
        return this;
    }

    public R data(Map<String ,Object> map){
        this.setData(map);
        return this;
    }



}
