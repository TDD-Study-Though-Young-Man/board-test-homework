package com.member.homework.controller.admin;

import com.member.homework.common.dto.ApiResponse;
import com.member.homework.service.admin.GrantRoleService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class GrantRoleController {

    private final GrantRoleService grantRoleService;

    @PostMapping("/api/admin/users/{userId}/roles")
    public ResponseEntity<ApiResponse<Long>> grantRoleToMember
            (@PathVariable("userId") @Positive Long userId, @RequestBody List<String> roleList) {
        grantRoleService.grantRoleToMember(userId, roleList);
        return ResponseEntity.ok(ApiResponse.ok(userId));
    }
}
