package com.member.homework.service.comment;

import com.member.homework.domain.Comment;
import com.member.homework.domain.Post;
import com.member.homework.repository.comment.CommentRepository;
import com.member.homework.repository.post.PostRepository;
import com.member.homework.service.comment.dto.ModifyCommentRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ModifyCommentServiceTest {

    @Autowired
    private ModifyCommentService modifyCommentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void 사용자는_댓글을_수정할_수_있다() {
        // given
        Comment comment = Comment.builder()
                .author("테스트 댓글 작성자")
                .content("테스트 댓글 내용")
                .deleteYn(false)
                .activeYn(false)
                .build();
        commentRepository.save(comment);

        ModifyCommentRequest modifyCommentRequest = new ModifyCommentRequest(comment.getCommentId(), "테스트 댓글 수정된 내용", false);

        // when
        modifyCommentService.modifyComment(modifyCommentRequest);
        commentRepository.flush();

        // then
        Comment updatedComment = commentRepository.findById(comment.getCommentId()).orElseThrow();
        assertThat(updatedComment.getContent()).isEqualTo("테스트 댓글 수정된 내용");
    }

    @Test
    void 사용자는_삭제된_글의_댓글을_수정할_수_없다() {
        // given
        Post deletedPost = Post.builder()
                .title("삭제된 글")
                .content("삭제된 글 내용")
                .author("삭제된 글 작성자")
                .deleteYn(true)
                .build();
        postRepository.save(deletedPost);

        Comment deletedPostComment = Comment.builder()
                .author("테스트 댓글 작성자")
                .content("테스트 댓글 내용")
                .deleteYn(false)
                .activeYn(true)
                .build();
        commentRepository.save(deletedPostComment);

        ModifyCommentRequest modifyCommentRequest = new ModifyCommentRequest(deletedPostComment.getCommentId(), "테스트 댓글 수정된 내용", false);

        // when
        Throwable thrown = catchThrowable(() -> modifyCommentService.modifyComment(modifyCommentRequest));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("삭제된 글의 댓글은 수정할 수 없습니다.");
    }

    @Test
    void 댓글은_작성자_본인만_수정할_수_있다() {
        // given
        Comment comment = Comment.builder()
                .author("테스트 댓글 작성자")
                .content("테스트 댓글 내용")
                .deleteYn(false)
                .activeYn(true)
                .build();
        commentRepository.save(comment);
        ModifyCommentRequest modifyCommentRequest = new ModifyCommentRequest(comment.getCommentId(), "테스트 댓글 수정된 내용", false);

        // when
        Throwable thrown = catchThrowable(() -> modifyCommentService.modifyComment(modifyCommentRequest));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("댓글은 작성자 본인만 수정할 수 있습니다.");
    }
}
