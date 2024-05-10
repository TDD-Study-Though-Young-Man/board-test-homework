package com.tdd.board.util;

import com.tdd.board.member.domain.Member;
import com.tdd.board.member.domain.MemberRole;
import com.tdd.board.member.domain.Role;
import com.tdd.board.member.dto.request.LoginMemberCommand;
import com.tdd.board.member.dto.request.ModifyMemberCommand;
import com.tdd.board.member.dto.request.RegisterMemberCommand;
import com.tdd.board.member.repository.MemberRepository;
import com.tdd.board.member.repository.MemberRoleRepository;
import com.tdd.board.member.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestUtil {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Long createMember(String id, String password, String roleName, String name) {
        MemberRole memberRole = MemberRole.of();
        memberRoleRepository.save(memberRole);

        Role role = Role.of(roleName);
        role.setMemberRole(memberRole);
        roleRepository.save(role);

        Member member = Member.of(id, passwordEncoder.encode(password), name);
        member.setMemberRole(memberRole);
        return memberRepository.save(member).getMemberId();
    }

    public void createRole(String roleName) {
        roleRepository.save(Role.of(roleName));
    }

    public void saveAllMembers(List<Member> memberList) {
        memberRepository.saveAll(memberList);
    }

    public LoginMemberCommand createLoginMemberCommand(String id, String password) {
        return new LoginMemberCommand(id, password);
    }

    public RegisterMemberCommand createRegisterMemberCommand(String id, String password, String name) {
        return new RegisterMemberCommand(id, password, name);
    }

    public ModifyMemberCommand createModifyMemberCommand(String id, String password, String name) {
        return new ModifyMemberCommand(id, password, name);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}