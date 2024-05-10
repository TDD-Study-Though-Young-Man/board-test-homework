package com.tdd.board.member.controller;

import com.tdd.board.common.BaseResponse;
import com.tdd.board.member.dto.request.LoginMemberCommand;
import com.tdd.board.member.service.AdminLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminLoginController {

    private final AdminLoginService adminLoginService;

    @PostMapping("/api/admin/login")
    public ResponseEntity<BaseResponse<String>> login(@RequestBody @Valid final LoginMemberCommand loginMemberCommand) {

        return ResponseEntity.ok(
                BaseResponse.ok(adminLoginService.login(loginMemberCommand))
        );
    }
}
