package com.member.homework.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", columnDefinition = "INTEGER", nullable = false)
    private Long post_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "title", columnDefinition = "VARCHAR(100)", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "author", columnDefinition = "VARCHAR(30)", nullable = false)
    private String author;

    @Column(name = "delete_yn", columnDefinition = "BOOLEAN", nullable = false)
    private boolean deleteYn;

    @Column(name = "active_yn", columnDefinition = "BOOLEAN", nullable = false)
    private boolean activeYn;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String title, String content, String author, boolean deleteYn, boolean activeYn) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.deleteYn = deleteYn;
        this.activeYn = activeYn;
    }
}
