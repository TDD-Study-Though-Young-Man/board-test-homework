package com.member.homework.exception;

import lombok.*;
import org.springframework.http.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    ID_CANNOT_BE_EMPTY(BAD_REQUEST, "아이디는 비어있을 수 없습니다."),
    PASSWORD_CANNOT_BE_EMPTY(BAD_REQUEST, "비밀번호는 비어있을 수 없습니다."),
    NAME_CANNOT_BE_EMPTY(BAD_REQUEST, "이름은 비어있을 수 없습니다."),
    ID_CANNOT_BE_LESS_THAN_ZERO(BAD_REQUEST, "아이디는 0 미만일 수 없습니다"),
    PARAMETER_INVALID(BAD_REQUEST, "유효하지 않은 값입니다."),

    /* 403 FORBIDDEN : 접근 권한 제한 */
    /* Valid : 유효한 */

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    CATEGORY_NOT_FOUND_BY_ID(NOT_FOUND, "존재하지 않는 카테고리 ID 입니다.");

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    /* DUPLICATE : (다른 무엇과) 똑같은 */


    /* 500 : */

    private final HttpStatus httpStatus;
    private final String detail;
}