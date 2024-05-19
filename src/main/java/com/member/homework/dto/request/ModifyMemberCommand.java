package com.member.homework.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ModifyMemberCommand(

    @NotBlank(message = "아이디는 비어있을 수 없습니다.")
    String id,

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    String password,

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    String name
) {}
