package com.member.homework.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record GrantRoleCommand(
    @NotEmpty(message = "권한 리스트는 비어있을 수 없습니다.")
    List<String> roleList
) {}
