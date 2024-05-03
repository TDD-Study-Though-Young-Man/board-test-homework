package com.tym.board.member.service;

import com.tym.board.member.domain.Member;
import com.tym.board.member.domain.MemberRole;
import com.tym.board.member.domain.Role;
import com.tym.board.member.dto.request.GrantRoleCommand;
import com.tym.board.member.repository.MemberRepository;
import com.tym.board.member.repository.MemberRoleRepository;
import com.tym.board.member.repository.RoleRepository;
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

    public void grantRoleToMember(Long memberId, List<GrantRoleCommand> roleList) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원에게 권한을 부여할 수 없습니다."));


        List<Role> findRoleList = roleRepository.findAllByRoleNameIn(
                roleList.stream().map(GrantRoleCommand::role)
                        .toList());

        if (roleList.size() != findRoleList.size()) {
            throw new IllegalArgumentException("유효하지 않은 권한 부여 시도는 허용되지 않습니다.");
        }

        findRoleList.forEach(role -> {
            MemberRole memberRole = MemberRole.of();
            memberRoleRepository.save(memberRole);

            role.setMemberRole(memberRole);
            findMember.setMemberRole(memberRole);
        });
    }
}
