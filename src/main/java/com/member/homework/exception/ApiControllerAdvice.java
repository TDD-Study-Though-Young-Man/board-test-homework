package com.member.homework.exception;

import com.member.homework.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class ApiControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Object>> bindException(BindException e) {

        Map<String, List<String>> errorMap = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        return ResponseEntity.badRequest()
                .body(ApiResponse.of(BAD_REQUEST, BAD_REQUEST.getReasonPhrase(), errorMap));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<String>> handlerMethodValidationException() {

        return ResponseEntity.badRequest()
                .body(ApiResponse.of(BAD_REQUEST, ErrorCode.PARAMETER_INVALID.getDetail()));
    }
}
