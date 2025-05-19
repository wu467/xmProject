package com.xiaomi.common.base.exception;

import lombok.Getter;

/**
 * 基础业务异常类
 */

@Getter
public class BusinessException extends RuntimeException {
    private final Integer code;
    
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}