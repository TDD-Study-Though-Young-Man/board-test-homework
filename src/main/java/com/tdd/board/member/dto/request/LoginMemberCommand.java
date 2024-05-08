package com.tdd.board.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginMemberCommand {

    @NotBlank(message = "아이디는 비어있을 수 없습니다.")
    private final String id;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    private final String password;

}
