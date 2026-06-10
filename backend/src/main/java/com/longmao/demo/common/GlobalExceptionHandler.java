package com.longmao.demo.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 * 统一捕获系统异常并返回标准 JSON 格式
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获并处理所有未受控的顶级异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e, HttpServletRequest request) {
        log.error("系统运行异常 [URI: {}]: ", request.getRequestURI(), e);
        
        String message = e.getMessage();
        if (message == null || message.trim().isEmpty()) {
            message = "系统内部异常，请稍后再试";
        }
        return Result.error(500, message);
    }
    
    /**
     * 捕获并处理业务逻辑异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e) {
        log.warn("业务处理告警: {}", e.getMessage());
        return Result.error(500, e.getMessage());
    }

    /**
     * 捕获并处理请求参数校验异常 (Spring Validation)
     */
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public Result<String> handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("参数校验未通过: {}", message);
        return Result.error(400, message);
    }
}
