package com.member.homework.util;

import com.member.homework.domain.Member;
import com.member.homework.domain.MemberRole;
import com.member.homework.domain.Role;
import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.dto.request.ModifyMemberCommand;
import com.member.homework.dto.request.RegisterMemberCommand;
import com.member.homework.repository.MemberRepository;
import com.member.homework.repository.MemberRoleRepository;
import com.member.homework.repository.RoleRepository;
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
