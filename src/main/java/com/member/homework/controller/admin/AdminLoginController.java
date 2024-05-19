package com.member.homework.controller.admin;

import com.member.homework.common.dto.ApiResponse;
import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.service.admin.AdminLoginService;
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
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid final LoginMemberCommand loginMemberCommand) {

        return ResponseEntity.ok(
                ApiResponse.ok(adminLoginService.login(loginMemberCommand))
        );
    }
}
