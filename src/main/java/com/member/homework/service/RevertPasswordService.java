package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.PasswordUtil;
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
