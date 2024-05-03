package com.member.homework.controller;

import com.member.homework.common.BaseResponse;
import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.service.AdminLoginService;
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
