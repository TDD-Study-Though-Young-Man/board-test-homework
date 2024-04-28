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
public class Role extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", columnDefinition = "INTEGER")
    private Long id;

    @Column(name = "role_name", columnDefinition = "VARCHAR(30)")
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private List<MemberRole> memberRoles = new ArrayList<>();

    @Builder
    private Role(String roleName) {
        this.roleName = roleName;
    }

    public void setMemberRole(MemberRole memberRole) {
        this.getMemberRoles().add(memberRole);
        memberRole.setRole(this);
    }

    public static Role of(String roleName) {
        return Role.builder()
                .roleName(roleName)
                .build();
    }
}
