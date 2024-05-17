package com.member.homework.service.comment.dto;

public record ModifyCommentRequest(
        Long commentId,
        String content,
        boolean deleteYn
) {
}
