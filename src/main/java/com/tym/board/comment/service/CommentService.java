package com.tym.board.comment.service;

import com.tym.board.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {


    public Comment createComment(Comment comment) {
        return null;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return null;
    }

    public Comment updateComment(Comment comment) {
        return null;
    }

    public void deleteComment(Long commentId) {

    }
}
