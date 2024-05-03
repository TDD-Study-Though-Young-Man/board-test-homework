package com.tym.board.comment.repository;

import com.tym.board.comment.domain.Comment;
import com.tym.board.post.domain.Post;
import com.tym.board.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    @DisplayName("새로운 댓글을 추가할 수 있어야 한다.")
    void testCreateComment() {
        //given
        Post post = Post.builder()
                .contents("newPost")
                .build();
        postRepository.save(post);

        Optional<Post> newPost = postRepository.findByContents("newPost");

        Long postId = newPost.get().getPostId();

        Comment newComment = Comment.builder()
                .postId(postId)
                .contents("newComment")
                .deleteYn(false)
                .build();

        //when
        commentRepository.save(newComment);

        //then
        Comment savedComment = commentRepository.findByContents("newComment").orElseThrow();
        assertThat(savedComment.getContents()).isEqualTo("newComment");
        assertThat(savedComment.getDeleteYn()).isFalse();
    }

    @Test
    @DisplayName("기존 댓글을 수정할 수 있어야 한다.")
    void testUpdateComment() {
        //given
        Post post = Post.builder()
                .contents("newPost")
                .build();
        postRepository.save(post);

        String newContents = "Updated comment";

        Optional<Post> newPost = postRepository.findByContents("newPost");

        Long postId = newPost.get().getPostId();

        Comment newComment = Comment.builder()
                .postId(postId)
                .contents("newComment")
                .deleteYn(false)
                .build();

        commentRepository.save(newComment);

        Comment comment = commentRepository.findByContents("newComment").get();

        comment.updateCommentContents(newContents);

        //when
        Comment updatedComment = commentRepository.findByContents(newContents).get();

        //then
        assertThat(updatedComment.getContents()).isEqualTo(newContents);
    }


    @Test
    @DisplayName("기존 댓글을 삭제할 수 있어야 한다.")
    void testDeleteComment() {
        //given
        Post post = Post.builder()
                .contents("newPost")
                .build();
        postRepository.save(post);

        Optional<Post> newPost = postRepository.findByContents("newPost");

        Long postId = newPost.get().getPostId();

        Comment newComment = Comment.builder()
                .postId(postId)
                .contents("newComment")
                .deleteYn(false)
                .build();
        commentRepository.save(newComment);

        Comment comment = commentRepository.findByContents("newComment").get();

        //when
        comment.updateCommentDeleted(true);
        Comment deletedComment = commentRepository.findByContents("newComment").get();

        //then
        assertThat(deletedComment).isNotNull();
        assertThat(deletedComment.getDeleteYn()).isTrue();
    }
}
