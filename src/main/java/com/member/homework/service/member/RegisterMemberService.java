package com.member.homework.service.member;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.RegisterMemberCommand;
import com.member.homework.repository.member.MemberRepository;
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
                command.getLoginId(),
                passwordUtil.encodePassword(command.getPassword()),
                command.getName()
        ));

        return saveMember.getMemberId();
    }

    private void checkDuplicateId(RegisterMemberCommand command) {
        if (memberRepository.existsMemberByLoginId(command.getLoginId())) {
            throw new IllegalArgumentException("이미 가입된 ID 입니다.");
        }
    }
}
