package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.RegisterMemberCommand;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterMemberService {

    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public Long register(RegisterMemberCommand command) {
        checkDuplicateId(command);

        Member saveMember = memberRepository.save(Member.of(
                command.id(),
                passwordUtil.encodePassword(command.password()),
                command.name()
        ));

        return saveMember.getMemberId();
    }

    private void checkDuplicateId(RegisterMemberCommand command) {
        if (memberRepository.existsMemberById(command.id())) {
            throw new IllegalArgumentException("이미 가입된 ID 입니다.");
        }
    }
}
