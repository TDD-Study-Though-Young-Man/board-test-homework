package com.member.homework.controller.admin;

import com.member.homework.dto.response.ApiResponse;
import com.member.homework.service.RemoveMemberService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RemoveMemberController {

    private final RemoveMemberService removeMemberService;

    @DeleteMapping("/api/admin/users/{userId}")
    public ResponseEntity<ApiResponse<Long>> removeMember(@PathVariable @Positive Long userId) {
         removeMemberService.removeMember(userId);
         return ResponseEntity.ok(ApiResponse.ok(userId));
    }
}
