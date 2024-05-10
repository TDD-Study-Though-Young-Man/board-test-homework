package com.tdd.board.member.service;

import com.tdd.board.member.dto.response.MemberDto;
import com.tdd.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadMemberService {

    private final MemberRepository memberRepository;

    public List<MemberDto> loadAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(member -> new MemberDto(member.getId(), member.getName()))
                .toList();
    }
}
