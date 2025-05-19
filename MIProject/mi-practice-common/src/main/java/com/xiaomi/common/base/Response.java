package com.xiaomi.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用响应结果
 * @param <T>
 */
@Data
@NoArgsConstructor
public class Response<T> {

    private Integer code;
    private String message;
    private Boolean success;
    private T data;

    // 成功的构造方法
    public Response(Integer code, String message, Boolean success, T data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    // 失败的构造方法
    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.success = false;
    }

    // 成功-有数据响应
    public static <T> Response<T> success(T data) {
        return new Response<>(200,  "success", true, data);
    }

    // 成功-无数据响应
    public static <Void> Response<Void> success() {
        return new Response<>(200,  "success", true, null);
    }

    // 失败-带自定义状态码
    public static <T> Response<T> fail(Integer code, String message) {
        return new Response<>(code, message);
    }

    // 失败-默认400状态码
    public static <T> Response<T> fail(String message) {
        return new Response<>(400, message);
    }
}