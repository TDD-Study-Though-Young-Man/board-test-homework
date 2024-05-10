package com.member.homework.service.member;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.ModifyMemberCommand;
import com.member.homework.repository.member.MemberRepository;
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
        checkDuplicateId(command.getId());

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원의 정보는 수정할 수 없습니다."));


        findMember.updateMember(command.getId(),
                passwordUtil.encodePassword(command.getPassword()), command.getName());
    }

    private void checkDuplicateId(String loginId) {
        if (memberRepository.existsMemberByLoginId(loginId)) {
            throw new IllegalArgumentException("중복된 ID로 정보 변경은 불가능합니다.");
        }
    }

}
