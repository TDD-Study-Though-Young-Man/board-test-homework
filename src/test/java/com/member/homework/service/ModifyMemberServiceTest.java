package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.ModifyMemberCommand;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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
    void 사용자가_회원_정보_수정을_시도하면_정보가_수정되어야_한다() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "MEMBER", "궁햄");

        ModifyMemberCommand command = testUtil.createModifyMemberCommand("mb2", "1236", "궁햄2");

        // when
        modifyMemberService.modifyMember(memberId, command);
        Member updateMember = memberRepository.findByLoginId("mb2").orElseThrow();

        // then
        Assertions.assertThat(updateMember.getLoginId()).isEqualTo("mb2");
        Assertions.assertThat(updateMember.getName()).isEqualTo("궁햄2");
        Assertions.assertThat(passwordEncoder.matches("1236", updateMember.getPassword())).isTrue();

    }


    @Test
    void 사용자가_중복된_ID로_회원_정보를_수정하려고_시도하면_수정이_실패해야_한다() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "MEMBER", "궁햄");

        ModifyMemberCommand command = testUtil.createModifyMemberCommand("mb1", "1236", "궁햄2");

        // when -> then
        Assertions.assertThatThrownBy(() -> modifyMemberService.modifyMember(memberId, command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 ID로 정보 변경은 불가능합니다.");

    }



}