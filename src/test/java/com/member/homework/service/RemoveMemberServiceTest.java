package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.domain.MemberRole;
import com.member.homework.repository.MemberRepository;
import com.member.homework.repository.MemberRoleRepository;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("관리자는 회원을 삭제할 수 있어야 한다.")
    void removeMemberTest() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");

        // when
        removeMemberService.removeMember(memberId);
        Optional<Member> findMember = memberRepository.findById("mb1");

        List<MemberRole> memberRoleList = memberRoleRepository.findAll();

        // then
        assertThat(findMember.isEmpty()).isTrue();
        assertThat(memberRoleList).isEmpty();

    }

    @Test
    @DisplayName("존재하지 않는 회원의 삭제 시도는 실패해야 한다.")
    void notExistsMemberRemoveTest() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");

        // when -> then
        assertThatThrownBy(() -> removeMemberService.removeMember(memberId + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원은 삭제할 수 없습니다.");
    }

}