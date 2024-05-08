package com.tdd.board.exception;

import com.tdd.board.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionCatcher {

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<BaseResponse<String>> handleBindException(BindException exception) {
        log.error("예외가 발생했어요 무슨 예외일까? {}", exception.toString());

        return ResponseEntity.badRequest()
                .body(BaseResponse.badRequest(
                                exception.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                );
    }
}
