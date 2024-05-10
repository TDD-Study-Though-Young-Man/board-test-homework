package com.member.homework.service.admin;

import com.member.homework.domain.Member;
import com.member.homework.domain.MemberRole;
import com.member.homework.domain.Role;
import com.member.homework.repository.member.MemberRepository;
import com.member.homework.repository.member.MemberRoleRepository;
import com.member.homework.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GrantRoleService {

    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final MemberRepository memberRepository;

    public void grantRoleToMember(Long memberId, List<String> grantRoleList) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원에게 권한을 부여할 수 없습니다."));

        List<Role> memberRoleList = roleRepository.findAllByRoleNameIn(grantRoleList);
        checkIsValidGrantRequest(grantRoleList.size(), memberRoleList.size());

        // MemberRole 테이블과 매핑하여 권한 부여 내역 저장
        memberRoleList.forEach(role -> {
            MemberRole memberRole = MemberRole.of();
            memberRoleRepository.save(memberRole);

            role.setMemberRole(memberRole);
            findMember.setMemberRole(memberRole);
        });
    }

    private void checkIsValidGrantRequest(int grantRoleListSize, int memberRoleListSize) {
        if (grantRoleListSize != memberRoleListSize) {
            throw new IllegalArgumentException("유효하지 않은 권한 부여 시도는 허용되지 않습니다.");
        }
    }
}
