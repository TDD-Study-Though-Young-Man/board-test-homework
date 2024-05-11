package com.member.homework.service.comment;

import com.member.homework.service.comment.dto.CommentResponseDto;
import com.member.homework.service.comment.dto.CreateCommentRequest;
import com.member.homework.service.comment.dto.ModifyCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    public CommentResponseDto createCommentForPost(CreateCommentRequest createCommentRequest) {
        return null;
    }

    public List<CommentResponseDto> loadCommentsForPost(Long postId) {
        return null;
    }

    public CommentResponseDto modifyCommentForPost(ModifyCommentRequest modifyCommentRequest) {
        return null;
    }

    public void removeCommentFromPost(Long commentId) {
    }
}
