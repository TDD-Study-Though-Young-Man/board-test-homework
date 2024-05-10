package com.member.homework.service.member;

import com.member.homework.domain.Member;
import com.member.homework.dto.request.RegisterMemberCommand;
import com.member.homework.repository.member.MemberRepository;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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
    void 관리자는_회원을_생성할_수_있어야_한다() {
        // given
        String loginId = "mb1";
        String name = "궁햄";
        String password = "1234";
        RegisterMemberCommand command = testUtil.createRegisterMemberCommand(loginId, "1234", name);

        // when
        Long memberId = registerMemberService.register(command);
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow();

        // then
        assertThat(member.getMemberId()).isEqualTo(memberId);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(passwordEncoder.matches(password, member.getPassword())).isTrue();
    }

    @Test
    void 존재하는_ID로_가입을_시도하면_가입이_실패해야_한다() {
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