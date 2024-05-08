package com.tdd.board.member.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GrantRoleCommand {

    @NotEmpty(message = "권한 리스트는 비어있을 수 없습니다.")
    private final List<String> roleList;
}
