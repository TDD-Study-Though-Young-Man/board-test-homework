package com.tym.board.member.service;

import com.tym.board.member.domain.Member;
import com.tym.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadMemberService {

    private final MemberRepository memberRepository;

    public List<Member> loadAllMembers() {
        return memberRepository.findAll();
    }
}
