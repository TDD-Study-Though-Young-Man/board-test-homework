package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.RegisterMemberCommand;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class RegisterMemberServiceTest {

    @Autowired
    private RegisterMemberService registerMemberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("관리자는 회원을 생성할 수 있어야 한다.")
    void registerTest() {
        // given
        String id = "mb1";
        String name = "궁햄";
        String password = "1234";
        RegisterMemberCommand command = testUtil.createRegisterMemberCommand(id, "1234", name);

        // when
        Long memberId = registerMemberService.register(command);
        Member member = memberRepository.findById(id)
                .orElseThrow();

        // then
        assertThat(member.getMemberId()).isEqualTo(memberId);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(passwordEncoder.matches(password, member.getPassword())).isTrue();
    }

    @Test
    @DisplayName("존재하는 ID로 가입을 시도하면 가입이 실패해야 한다.")
    void duplicateIdRegisterTest() {
        // given
        String id = "mb1";
        String name = "궁햄2";
        RegisterMemberCommand command = testUtil.createRegisterMemberCommand(id, "1234", name);
        RegisterMemberCommand duplicateCommand = testUtil.createRegisterMemberCommand(id, "9999", "궁햄1");

        registerMemberService.register(command);

        // when -> then
        assertThatThrownBy(() -> registerMemberService.register(duplicateCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 가입된 ID 입니다.");
    }
}