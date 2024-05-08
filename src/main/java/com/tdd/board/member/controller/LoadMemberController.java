package com.tdd.board.member.controller;

import com.tdd.board.common.BaseResponse;
import com.tdd.board.member.dto.response.MemberDto;
import com.tdd.board.member.service.LoadMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoadMemberController {

    private final LoadMemberService loadMemberService;

    @GetMapping("/api/admin/users")
    public ResponseEntity<BaseResponse<List<MemberDto>>> loadAllMembers() {
        return ResponseEntity.ok(
                BaseResponse.ok(loadMemberService.loadAllMembers())
        );
    }
}
