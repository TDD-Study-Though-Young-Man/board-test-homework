package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.ModifyMemberCommand;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.PasswordUtil;
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
        checkDuplicateId(command.id());

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원의 정보는 수정할 수 없습니다."));


        findMember.updateMember(command.id(),
                passwordUtil.encodePassword(command.password()), command.name());
    }

    private void checkDuplicateId(String id) {
        if (memberRepository.existsMemberById(id)) {
            throw new IllegalArgumentException("중복된 ID로 정보 변경은 불가능합니다.");
        }
    }

}
