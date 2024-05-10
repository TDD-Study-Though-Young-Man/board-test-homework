package com.tdd.board.member.service;

import com.tdd.board.member.domain.Member;
import com.tdd.board.member.dto.request.ModifyMemberCommand;
import com.tdd.board.member.repository.MemberRepository;
import com.tdd.board.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyMemberService {

    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public void modifyMember(Long memberId, ModifyMemberCommand command) {
        checkDuplicateId(command.getId());

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원의 정보는 수정할 수 없습니다."));


        findMember.updateMember(command.getId(),
                passwordUtil.encodePassword(command.getPassword()), command.getName());
    }

    private void checkDuplicateId(String id) {
        if (memberRepository.existsMemberById(id)) {
            throw new IllegalArgumentException("중복된 ID로 정보 변경은 불가능합니다.");
        }
    }

}