package com.member.homework.service.member;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.repository.member.MemberRepository;
import com.member.homework.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLoginService {

    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public String login(LoginMemberCommand command) {
        Member member = memberRepository.findByLoginId(command.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        passwordUtil.checkPassword(command.getPassword(), member.getPassword());
        return "jwttoken";
    }
}
