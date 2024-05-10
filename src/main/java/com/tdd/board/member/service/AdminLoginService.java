package com.tdd.board.member.service;

import com.tdd.board.member.domain.Member;
import com.tdd.board.member.dto.request.LoginMemberCommand;
import com.tdd.board.member.repository.MemberRepository;
import com.tdd.board.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLoginService {

    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public String login(LoginMemberCommand command) {
        Member findMember = memberRepository.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        passwordUtil.checkPassword(command.getPassword(), findMember.getPassword());
        checkIsAdmin(findMember);

        return "jwttoken";
    }

    private void checkIsAdmin(Member findMember) {
        boolean isAdmin = findMember.getMemberRoles()
                .stream()
                .anyMatch(role -> role.getRole().getRoleName().equals("ADMIN"));

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자 권한이 없습니다. 관리자 페이지에 진입할 수 없습니다.");
        }
    }
}
