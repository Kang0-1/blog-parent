package com.kang.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    private Result(){}

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }


    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }

    public Result code(int code){
        this.setCode(code);
        return this;
    }

    public Result msg(String msg){
        this.setMsg(msg);
        return this;
    }


}
