package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.PasswordUtil;
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
