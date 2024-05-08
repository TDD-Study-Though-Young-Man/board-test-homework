package com.tdd.board.member.service;

import com.tdd.board.member.domain.Member;
import com.tdd.board.member.dto.request.LoginMemberCommand;
import com.tdd.board.member.repository.MemberRepository;
import com.tdd.board.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLoginService {

    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public String login(LoginMemberCommand command) {
        Member member = memberRepository.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        passwordUtil.checkPassword(command.getPassword(), member.getPassword());
        return "jwttoken";
    }
}
