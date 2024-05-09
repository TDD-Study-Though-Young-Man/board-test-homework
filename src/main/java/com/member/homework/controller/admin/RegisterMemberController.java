package com.member.homework.controller.admin;

import com.member.homework.dto.request.RegisterMemberCommand;
import com.member.homework.dto.response.ApiResponse;
import com.member.homework.service.RegisterMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RegisterMemberController {

    private final RegisterMemberService registerMemberService;

    @PostMapping("/api/admin/users")
    public ResponseEntity<ApiResponse<Long>> register(@Valid @RequestBody RegisterMemberCommand registerMemberCommand) {
             return ResponseEntity.ok(ApiResponse.ok(registerMemberService.register(registerMemberCommand)));
    }
}
