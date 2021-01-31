package com.bjlemon.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 */
@Data
@AllArgsConstructor //全部参数构造方法
@NoArgsConstructor  //无参构造方法
public class LemonException extends RuntimeException{

    private Integer code;//状态码
    private String msg;//异常信息
}
