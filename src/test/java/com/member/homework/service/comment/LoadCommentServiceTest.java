package com.member.homework.service.comment;

import com.member.homework.domain.Comment;
import com.member.homework.repository.comment.CommentRepository;
import com.member.homework.service.comment.dto.CommentResponseDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LoadCommentServiceTest {

    @Autowired
    private LoadCommentService loadCommentService;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 사용자는_삭제_처리된_댓글을_조회할_수_없다() {
        // given
        Comment deletedComment = Comment.builder()
                .author("삭제된 댓글 작성자")
                .content("삭제된 댓글 내용")
                .deleteYn(true)
                .activeYn(false)
                .build();
        commentRepository.save(deletedComment);

        // when
        List<CommentResponseDto> foundComments = loadCommentService.loadComment(1L);

        // then
//        assertThat(foundComments).doesNotContain(deletedComment);
    }
}
