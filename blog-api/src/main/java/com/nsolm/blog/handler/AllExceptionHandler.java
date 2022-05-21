package com.nsolm.blog.handler;


import com.nsolm.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/1 17:06
 * @Description :
 */

//对加了@Controller注解方法进行拦截处理 AOP的实现
@RestControllerAdvice
public class AllExceptionHandler {

    //进行异常处理,处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
