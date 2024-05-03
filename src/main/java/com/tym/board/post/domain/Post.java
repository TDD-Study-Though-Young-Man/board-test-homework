package com.tym.board.post.domain;

import com.tym.board.comment.domain.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", columnDefinition = "INTEGER")
    private Long comment_id;

    @Column(name = "contents", columnDefinition = "VARCHAR(200)")
    private String contents;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Builder
    public Post(Long comment_id, String contents) {
        this.comment_id = comment_id;
        this.contents = contents;
    }
}
