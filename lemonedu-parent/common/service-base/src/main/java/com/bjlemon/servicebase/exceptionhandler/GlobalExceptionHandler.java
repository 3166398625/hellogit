package com.bjlemon.servicebase.exceptionhandler;

import com.bjlemon.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //出现什么异常时执行该方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局处理异常");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("运算异常");
    }

    //自定义异常
    @ExceptionHandler(LemonException.class)
    @ResponseBody //返回数据
    public R error(LemonException e){
        e.printStackTrace();
        log.error(e.getMsg());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
