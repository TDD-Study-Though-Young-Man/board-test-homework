package com.tym.board.member.service;

import com.tym.board.member.domain.Member;
import com.tym.board.member.dto.request.LoginMemberCommand;
import com.tym.board.member.repository.MemberRepository;
import com.tym.board.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLoginService {

    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public String login(LoginMemberCommand command) {
        Member member = memberRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        passwordUtil.checkPassword(command.password(), member.getPassword());
        return "jwttoken";
    }
}
