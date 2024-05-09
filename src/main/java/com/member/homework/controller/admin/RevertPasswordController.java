package com.member.homework.controller.admin;

import com.member.homework.dto.response.ApiResponse;
import com.member.homework.service.RevertPasswordService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RevertPasswordController {

    private final RevertPasswordService revertPasswordService;

    @GetMapping("/api/admin/users/{userId}/password")
    public ResponseEntity<ApiResponse<Long>> revertPassword(@PathVariable("userId") @Positive Long userId) {
        revertPasswordService.revertPassword(userId);
        return ResponseEntity.ok(ApiResponse.ok(userId));
    }
}
