package com.tdd.board.member.service;

import com.tdd.board.member.domain.Member;
import com.tdd.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RemoveMemberService {

    private final MemberRepository memberRepository;



    public void removeMember(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원은 삭제할 수 없습니다."));


        memberRepository.delete(findMember);
    }
}