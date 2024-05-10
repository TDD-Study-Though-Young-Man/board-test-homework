package com.member.homework.domain;

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
    @Column(name = "comment_id", columnDefinition = "INTEGER", nullable = false)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "author", columnDefinition = "VARCHAR(30)", nullable = false)
    private String author;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "delete_yn", columnDefinition = "BOOLEAN", nullable = false)
    private boolean deleteYn;

    @Column(name = "active_yn", columnDefinition = "BOOLEAN", nullable = false)
    private boolean activeYn;

    @Builder
    public Comment(String author, String content, boolean deleteYn, boolean activeYn) {
        this.author = author;
        this.content = content;
        this.deleteYn = deleteYn;
        this.activeYn = activeYn;
    }

    public void updateCommentContent(String content) {
        this.content = content;
    }

    public void updateCommentDeleted(Boolean deleteYn) {
        this.deleteYn = deleteYn;
    }
}
