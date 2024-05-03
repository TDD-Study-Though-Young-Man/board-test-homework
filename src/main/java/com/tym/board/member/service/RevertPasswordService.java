package com.tym.board.member.service;

import com.tym.board.member.domain.Member;
import com.tym.board.util.PasswordUtil;
import com.tym.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RevertPasswordService {

    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public String revertPassword(Long userId) {
        Member findMember = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원의 비밀번호는 초기화 할 수 없습니다."));

        String newPassword = UUID.randomUUID().toString();
        findMember.revertPassword(passwordUtil.encodePassword(newPassword));
        return newPassword;
    }
}
