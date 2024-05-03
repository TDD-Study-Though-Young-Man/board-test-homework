package com.member.homework.common;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

    private final T data;
    private final String message;
    private final int statusCode;


    private BaseResponse(T data, String message, int statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(data, "OK", 200);
    }

    public static <T> BaseResponse<T> badRequest(String message) {
        return new BaseResponse<>(null, message, 400);
    }


}
