package com.example.handler;

import com.example.constant.MessageConstant;
import com.example.exception.BaseException;
import com.example.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler//这个注解必须要加,全局异常处理器里面的方法名都是一样的
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        //根据异常类来获取它的信息
        String message = ex.getMessage();
        //然后判断这个异常信息是不是我们刚刚报错出来的那个用户名已存在的异常信息,如果是再进行后续操作
        if (message.contains("Duplicate entry")){
            //根据空格进行分割上面的那条错误信息,然后获取到重复的用户名zhangsan
            String[] split = message.split(" ");
            String username = split[2];
            //然后封装出提示信息代替这个异常信息返回给前端
            String msg = username + MessageConstant.ALREADY_EXISTS;
            //然后封装msg到Result对象中,返回给前端
            return Result.error(msg);
        }else {
            //如果异常读取出来的信息不匹配上面的信息,就说明不是用户已存在的问题,具体还不清楚
            return Result.error(MessageConstant.UNKNOWN_ERROR);//信息为"未知错误"封装到Result中返回
        }

    }
}