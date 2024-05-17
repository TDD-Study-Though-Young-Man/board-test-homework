package com.member.homework.service.comment;

import com.member.homework.domain.Comment;
import com.member.homework.repository.comment.CommentRepository;
import com.member.homework.service.comment.dto.CreateCommentRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreateCommentServiceTest {

    @Autowired
    private CreateCommentService createCommentService;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 사용자는_새로운_댓글을_작성할_수_있다() {
        // given
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(1L, 1L, "테스트 작성자", "테스트 내용", false, false);

        // when
        createCommentService.createComment(createCommentRequest);
        commentRepository.flush();
        Optional<Comment> foundCommentOpt = commentRepository.findByContent("테스트 내용");

        // then
        Comment foundComment = foundCommentOpt.orElseThrow();
        assertThat(foundComment).isNotNull();
        assertThat(foundComment)
                .extracting("author", "content", "deleteYn", "activeYn")
                .containsExactly("테스트 작성자", "테스트 내용", false, false);
    }

    @Test
    void 사용자는_존재하지_않는_글에_댓글을_작성할_수_없다() {
        // given
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(1L, 999L, "테스트 작성자", "테스트 내용", false, false);

        // when
        Throwable thrown = catchThrowable(() -> createCommentService.createComment(createCommentRequest));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 글에 대한 댓글은 작성할 수 없습니다.");
    }
}

