package com.member.homework.domain;

import com.member.homework.dto.request.ModifyMemberCommand;
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
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", columnDefinition = "INTEGER")
    private Long memberId;

    @Column(name = "login_id", columnDefinition = "VARCHAR(30)")
    private String loginId;

    @Column(name = "password", columnDefinition = "VARCHAR(200)")
    private String password;

    @Column(name = "name", columnDefinition = "VARCHAR(30)")
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberRole> memberRoles = new ArrayList<>();

    @Builder
    private Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    public void updateMember(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    public void revertPassword(String newPassword) {
        this.password = newPassword;
    }


    public void setMemberRole(MemberRole memberRole) {
        this.memberRoles.add(memberRole);
        memberRole.setMember(this);
    }

    public static Member of(String loginId, String password, String name) {
        return Member.builder()
                .loginId(loginId)
                .name(name)
                .password(password)
                .build();
    }
}
