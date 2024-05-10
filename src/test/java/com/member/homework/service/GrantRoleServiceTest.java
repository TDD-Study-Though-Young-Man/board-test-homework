package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    void 관리자는_사용자에게_권한을_부여할_수_있다() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");
        testUtil.createRole("MEMBER");
        testUtil.createRole("SUPER_ADMIN");

        // when
        grantRoleService.grantRoleToMember(memberId, List.of(
                "MEMBER",
                "SUPER_ADMIN"));

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
    void 부여하려는_권한_리스트_중_유효하지_않은_권한_부여시_권한_부여에_실패해야_한다() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");
        testUtil.createRole("SUPER_ADMIN");

        // when -> then
        assertThatThrownBy(() -> grantRoleService.grantRoleToMember(memberId,
                List.of("CEO"))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 권한 부여 시도는 허용되지 않습니다.");
    }
}