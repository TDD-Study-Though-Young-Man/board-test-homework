package com.member.homework.repository.comment;

import com.member.homework.domain.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private Comment comment;
    String commentContent = "테스트 댓글 내용";
    String commentAuthor = "테스트 댓글 작성자";

    @BeforeEach
    void setUp() {
        comment = Comment.builder()
                .author(commentAuthor)
                .content(commentContent)
                .deleteYn(false)
                .activeYn(true)
                .build();
    }

    @Test
    void 새로운_댓글을_추가할_수_있어야_한다() {
         //when
        Comment savedComment = commentRepository.save(comment);

        //then
        Comment foundComment = commentRepository.findById(savedComment.getCommentId()).orElseThrow();
        assertThat(foundComment.getAuthor()).isEqualTo(commentAuthor);
        assertThat(foundComment.getContent()).isEqualTo(commentContent);
        assertThat(foundComment.isDeleteYn()).isFalse();
        assertThat(foundComment.isActiveYn()).isTrue();
    }

    @Test
    void 기존_댓글을_수정할_수_있어야_한다() {
        //given
        Comment savedComment = commentRepository.save(comment);

        //when
        String newCommentContent = "테스트 수정 댓글 내용";

        savedComment.updateCommentContent(newCommentContent);
        Comment foundComment = commentRepository.findById(savedComment.getCommentId()).orElseThrow();

        //then
        assertThat(foundComment.getContent()).isEqualTo(newCommentContent);
    }


    @Test
    void 기존_댓글을_삭제할_수_있어야_한다() {
        //given
        Comment savedComment = commentRepository.save(comment);

        //when
        savedComment.updateCommentDeleted(true);
        Comment foundComment = commentRepository.findById(savedComment.getCommentId()).orElseThrow();

        //then
        assertThat(foundComment).isNotNull();
        assertThat(foundComment.isDeleteYn()).isTrue();
    }
}
