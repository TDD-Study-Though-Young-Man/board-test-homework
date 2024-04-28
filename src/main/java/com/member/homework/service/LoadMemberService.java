package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.repository.MemberRepository;
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
