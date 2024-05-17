package com.member.homework.service.comment;

import com.member.homework.repository.comment.CommentRepository;
import com.member.homework.service.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoadCommentService {

    private final CommentRepository commentRepository;

    public List<CommentResponseDto> loadComment(Long postId) {
        return null;
    }
}
