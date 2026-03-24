package com.stu.helloserver.exception;

import com.stu.helloserver.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理所有异常
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace(); // 开发阶段打印堆栈，便于调试
        return Result.error(500, "系统内部异常：" + e.getMessage());
    }

    // 可以针对特定异常单独处理
    // @ExceptionHandler(ArithmeticException.class)
    // public Result<String> handleArithmeticException(ArithmeticException e) {
    //     return Result.error(400, "算术运算异常：" + e.getMessage());
    // }
}
