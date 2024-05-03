package com.tym.board.member.service;

import com.tym.board.member.domain.Member;
import com.tym.board.member.dto.request.LoginMemberCommand;
import com.tym.board.member.repository.MemberRepository;
import com.tym.board.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLoginService {

    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public String login(LoginMemberCommand command) {
        Member findMember = memberRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        passwordUtil.checkPassword(command.password(), findMember.getPassword());
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
