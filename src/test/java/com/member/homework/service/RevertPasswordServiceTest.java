package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.repository.MemberRepository;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class RevertPasswordServiceTest {

    @Autowired
    private RevertPasswordService revertPasswordService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestUtil testUtil;

    @Test
    void 관리자는_사용자의_비밀번호를_초기화_할_수_있다() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");

        // when
        String revertedPassword = revertPasswordService.revertPassword(memberId);
        Member findMember = memberRepository.findById(memberId).orElseThrow();

        // then
        assertThat(testUtil.matches(revertedPassword, findMember.getPassword())).isTrue();
    }

    @Test
    void 존재하지_않는_사용자의_비밀번호는_초기화_할_수_없다() {
        // given
        Long memberId = testUtil.createMember("mb1", "1234", "ADMIN", "궁햄112");

        // when -> then
        assertThatThrownBy(() -> revertPasswordService.revertPassword(memberId + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원의 비밀번호는 초기화 할 수 없습니다.");
    }

}