package com.wangkaiping.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //所有参数构造方法
@NoArgsConstructor //无参数构造方法
public class MyExceptionOne extends RuntimeException {
    private Integer code; //状态码
    private String message; //异常信息
}
