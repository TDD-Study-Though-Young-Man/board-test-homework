package com.member.homework.service.admin;

import com.member.homework.domain.Member;
import com.member.homework.domain.MemberRole;
import com.member.homework.repository.member.MemberRepository;
import com.member.homework.repository.member.MemberRoleRepository;
import com.member.homework.service.admin.RemoveMemberService;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class RemoveMemberServiceTest {

    @Autowired
    private RemoveMemberService removeMemberService;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 관리자는_회원을_삭제할_수_있어야_한다() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");

        // when
        removeMemberService.removeMember(memberId);
        Optional<Member> findMember = memberRepository.findByLoginId("mb1");

        List<MemberRole> memberRoleList = memberRoleRepository.findAll();

        // then
        assertThat(findMember.isEmpty()).isTrue();
        assertThat(memberRoleList).isEmpty();

    }

    @Test
    void 존재하지_않는_회원의_삭제_시도는_실패해야_한다() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");

        // when -> then
        assertThatThrownBy(() -> removeMemberService.removeMember(memberId + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원은 삭제할 수 없습니다.");
    }

}