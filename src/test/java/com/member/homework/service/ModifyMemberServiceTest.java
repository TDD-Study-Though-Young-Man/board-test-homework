package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.ModifyMemberCommand;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ModifyMemberServiceTest {

    @Autowired
    private ModifyMemberService modifyMemberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("사용자가 회원 정보 수정을 시도하면 정보가 수정되어야 한다.")
    void modifyMemberTest() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "MEMBER", "궁햄");

        ModifyMemberCommand command = testUtil.createModifyMemberCommand("mb2", "1236", "궁햄2");

        // when
        modifyMemberService.modifyMember(memberId, command);
        Member updateMember = memberRepository.findById("mb2").orElseThrow();

        // then
        Assertions.assertThat(updateMember.getId()).isEqualTo("mb2");
        Assertions.assertThat(updateMember.getName()).isEqualTo("궁햄2");
        Assertions.assertThat(passwordEncoder.matches("1236", updateMember.getPassword())).isTrue();

    }


    @Test
    @DisplayName("사용자가 중복된 ID로 회원 정보를 수정하려고 시도하면 수정이 실패해야 한다.")
    void useDuplicateIdForModifyMemberTest() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "MEMBER", "궁햄");

        ModifyMemberCommand command = testUtil.createModifyMemberCommand("mb1", "1236", "궁햄2");

        // when -> then
        Assertions.assertThatThrownBy(() -> modifyMemberService.modifyMember(memberId, command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 ID로 정보 변경은 불가능합니다.");

    }



}