package com.member.homework.controller.member;

import com.member.homework.common.dto.ApiResponse;
import com.member.homework.dto.request.ModifyMemberCommand;
import com.member.homework.service.member.ModifyMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ModifyMemberController {

    private final ModifyMemberService modifyMemberService;

    @PatchMapping("/api/admin/users/{userId}")
    public ResponseEntity<ApiResponse<ModifyMemberCommand>>modifyMember(@PathVariable("userId") Long userId, @Valid @RequestBody ModifyMemberCommand modifyMemberCommand) {
            modifyMemberService.modifyMember(userId, modifyMemberCommand);
            return ResponseEntity.ok(ApiResponse.ok(modifyMemberCommand));
    }
}
