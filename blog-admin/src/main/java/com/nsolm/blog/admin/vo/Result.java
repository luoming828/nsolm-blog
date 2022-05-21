package com.nsolm.blog.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/1 12:30
 * @Description :
 */

@Data
//获取全部的构成函数
@AllArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);

    }
}
