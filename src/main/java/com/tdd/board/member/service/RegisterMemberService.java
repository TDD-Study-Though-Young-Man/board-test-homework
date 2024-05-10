package com.tdd.board.member.service;

import com.tdd.board.member.domain.Member;
import com.tdd.board.member.dto.request.RegisterMemberCommand;
import com.tdd.board.member.repository.MemberRepository;
import com.tdd.board.util.PasswordUtil;
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
                command.getId(),
                passwordUtil.encodePassword(command.getPassword()),
                command.getName()
        ));

        return saveMember.getMemberId();
    }

    private void checkDuplicateId(RegisterMemberCommand command) {
        if (memberRepository.existsMemberById(command.getId())) {
            throw new IllegalArgumentException("이미 가입된 ID 입니다.");
        }
    }
}
