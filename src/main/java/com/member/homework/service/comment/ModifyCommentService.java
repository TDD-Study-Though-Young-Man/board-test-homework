package com.member.homework.service.comment;

import com.member.homework.repository.comment.CommentRepository;
import com.member.homework.service.comment.dto.ModifyCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyCommentService {

    private final CommentRepository commentRepository;

    public void modifyComment(ModifyCommentRequest modifyCommentRequest) {

    }

}
