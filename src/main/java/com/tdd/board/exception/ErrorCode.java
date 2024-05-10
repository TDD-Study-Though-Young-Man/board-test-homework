package com.tdd.board.exception;

import lombok.*;
import org.springframework.http.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */

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