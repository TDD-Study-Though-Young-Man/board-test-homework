package com.tym.board.comment.domain;

import com.tym.board.member.domain.BaseTimeEntity;
import com.tym.board.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", columnDefinition = "INTEGER")
    private Long comment_id;

    @Column(name = "member_id", columnDefinition = "INTEGER")
    private Long memberId;

    @Column(name = "post_id", columnDefinition = "INTEGER")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @Column(name = "contents", columnDefinition = "VARCHAR(200)")
    private String contents;

    @Column(name = "delete_yn", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean deleteYn;

    @Column(name = "active_yn", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean activeYn;

    @Builder
    public Comment(Long comment_id, Long postId, String contents, Boolean deleteYn, Boolean activeYn) {
        this.comment_id = comment_id;
        this.postId = postId;
        this.contents = contents;
        this.deleteYn = deleteYn;
        this.activeYn = activeYn;
    }

    public void updateCommentContents(String contents) {
        this.contents = contents;
    }

    public void updateCommentDeleted(Boolean deleteYn) {
        this.deleteYn = deleteYn;
    }
}
