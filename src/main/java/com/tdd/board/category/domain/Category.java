package com.tdd.board.category.domain;

import com.tdd.board.member.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Builder
    private Category(String name, String description, Category parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
    }

    public static Category of(String name, String description, Category category) {
        return Category.builder()
                .name(name)
                .description(description)
                .parent(category)
                .build();
    }

    public void updateDetails(String name, String description, Category parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
    }
}
