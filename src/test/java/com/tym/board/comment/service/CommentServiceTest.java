package com.tym.board.comment.service;

import com.tym.board.comment.domain.Comment;
import com.tym.board.comment.repository.CommentRepository;
import com.tym.board.member.domain.Member;
import com.tym.board.member.repository.MemberRepository;
import com.tym.board.post.domain.Post;
import com.tym.board.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(Member.of("testUser", "test", "테스트사용자"));
        postRepository.save(Post.of("test post"));
    }

    @Test
    @DisplayName("사용자는 게시글에 신규 댓글을 작성할 수 있습니다.")
    void testCreateComment() {
        // given
        Post post = postRepository.findById(1L).orElseThrow();
        Member member = memberRepository.findById(1L).orElseThrow();
        Comment comment = Comment.builder()
                .contents("new comment")
                .postId(post.getPostId())
                .memberId(member.getMemberId())
                .deleteYn(false)
                .activeYn(true)
                .build();

        // when
        Comment savedComment = commentService.createComment(comment);

        // then
        assertThat(savedComment.getCommentId()).isNotNull();
        assertThat(savedComment.getActiveYn()).isTrue();
    }

    @Test
    @DisplayName("사용자는 게시글에 댓글들을 조회할 수 있습니다.")
    void testGetCommentsForPost() {
        // given
        Long postId = 1L;

        // when
        List<Comment> comments = commentService.getCommentsByPostId(postId);

        // then
        assertThat(comments).isNotEmpty();
        assertThat(comments).allSatisfy(comment -> {
            assertThat(comment.getPost().getPostId()).isEqualTo(postId);
            assertThat(comment.getActiveYn()).isTrue();
        });
    }

    @Test
    @DisplayName("사용자는 댓글을 수정할 수 있습니다.")
    void testUpdateComment() {
        // given
        Long commentId = 1L;
        Post post = postRepository.findById(1L).orElseThrow();
        Member member = memberRepository.findById(1L).orElseThrow();
        Comment comment = Comment.builder()
                .contents("Updated comment")
                .postId(post.getPostId())
                .memberId(member.getMemberId())
                .deleteYn(false)
                .activeYn(true)
                .build();

        // when
        Comment savedComment = commentService.updateComment(comment);

        // then
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getContents()).isEqualTo("Updated comment");
    }

    @Test
    @DisplayName("사용자는 댓글을 삭제할 수 있습니다.")
    void testDeleteComment() {
        // given
        Long commentId = 1L;

        // when
        commentService.deleteComment(commentId);

        // then
        assertThat(commentRepository.findById(commentId)).isNotEmpty();
        assertThat(commentRepository.findById(commentId).get().getActiveYn()).isFalse();
        assertThat(commentRepository.findById(commentId).get().getDeleteYn()).isTrue();
    }
}