package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.domain.Role;
import com.member.homework.dto.request.GrantRoleCommand;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class GrantRoleServiceTest {

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GrantRoleService grantRoleService;

    @Test
    @DisplayName("관리자는 사용자에게 권한을 부여할 수 있다.")
    void grantRoleToMemberTest() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");
        testUtil.createRole("MEMBER");
        testUtil.createRole("SUPER_ADMIN");

        // when
        grantRoleService.grantRoleToMember(memberId, List.of(
                new GrantRoleCommand("MEMBER"),
                new GrantRoleCommand("SUPER_ADMIN")
        ));

        Member findMember = memberRepository.findById(memberId).orElseThrow();
        List<String> roleNameList = findMember.getMemberRoles()
                .stream()
                .map(role -> role.getRole().getRoleName())
                .toList();

        // then
        assertThat(roleNameList)
                .contains("MEMBER", "SUPER_ADMIN", "ADMIN");

    }

    @Test
    @DisplayName("부여하려는 권한 리스트 중, 유효하지 않은 권한 부여시 권한 부여에 실패해야 한다.")
    void grantInvalidRoleToMemberTest() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");
        testUtil.createRole("SUPER_ADMIN");

        // when -> then
        assertThatThrownBy(() -> grantRoleService.grantRoleToMember(memberId,
                List.of(new GrantRoleCommand("CEO")))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 권한 부여 시도는 허용되지 않습니다.");
    }
}