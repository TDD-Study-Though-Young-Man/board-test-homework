package com.member.homework.service.comment;

import com.member.homework.repository.comment.CommentRepository;
import com.member.homework.service.comment.dto.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCommentService {

    private final CommentRepository commentRepository;

    public void createComment(CreateCommentRequest createCommentRequest) {

    }

}
