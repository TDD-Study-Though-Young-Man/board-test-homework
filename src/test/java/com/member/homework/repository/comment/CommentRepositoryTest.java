package com.member.homework.repository.comment;

import com.member.homework.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 새로운_댓글을_추가할_수_있어야_한다() {
        //given
        Comment comment = Comment.builder()
                .author("테스트 댓글 작성자")
                .content("테스트 댓글 내용")
                .deleteYn(false)
                .activeYn(true)
                .build();

         //when
        Comment savedComment = commentRepository.save(comment);

        //then
        Comment foundComment = commentRepository.findById(savedComment.getCommentId()).orElseThrow();
        assertThat(foundComment.getAuthor()).isEqualTo("테스트 댓글 작성자");
        assertThat(foundComment.getContent()).isEqualTo("테스트 댓글 내용");
        assertThat(foundComment.isDeleteYn()).isFalse();
        assertThat(foundComment.isActiveYn()).isTrue();
    }

    @Test
    void 기존_댓글을_수정할_수_있어야_한다() {
        //given
        Comment comment = Comment.builder()
                .author("테스트 댓글 작성자")
                .content("테스트 댓글 내용")
                .deleteYn(false)
                .activeYn(true)
                .build();
        Comment savedComment = commentRepository.save(comment);

        //when
        savedComment.updateCommentContent("테스트 수정 댓글 내용");
        Comment foundComment = commentRepository.findById(savedComment.getCommentId()).orElseThrow();

        //then
        assertThat(foundComment.getContent()).isEqualTo("테스트 수정 댓글 내용");
    }


    @Test
    void 기존_댓글을_삭제할_수_있어야_한다() {
        //given
        Comment comment = Comment.builder()
                .author("테스트 댓글 작성자")
                .content("테스트 댓글 내용")
                .deleteYn(false)
                .activeYn(true)
                .build();
        Comment savedComment = commentRepository.save(comment);

        //when
        savedComment.softDeleteComment();
        Comment foundComment = commentRepository.findById(savedComment.getCommentId()).orElseThrow();

        //then
        assertThat(foundComment).isNotNull();
        assertThat(foundComment.isDeleteYn()).isTrue();
    }
}
