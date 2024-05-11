package com.member.homework.service.comment.dto;

public record CreateCommentRequest(
        Long memberId,
        Long postId,
        String author,
        String content,
        boolean deleteYn,
        boolean activeYn
) {
}
