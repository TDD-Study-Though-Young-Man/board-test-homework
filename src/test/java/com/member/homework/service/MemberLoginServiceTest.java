package com.member.homework.service;

import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberLoginServiceTest {

    @Autowired
    private MemberLoginService memberLoginService;

    @Autowired
    private TestUtil testUtil;


    @Test
    void 사용자는_게시판_서비스에_로그인_할_수_있어야_한다() {
        // given
        testUtil.createMember("mb1", "1234", "MEMBER", "궁햄");
        LoginMemberCommand command = new LoginMemberCommand("mb1", "1234");

        // when
        String result = memberLoginService.login(command);

        // then
        assertThat(result).isEqualTo("jwttoken");
    }

    @Test
    void 로그인_시도한_아이디가_존재하지_않는_아이디라면_로그인이_실패한다() {
        // given
        testUtil.createMember("mb1", "1234", "MEMBER", "궁햄");
        LoginMemberCommand command = new LoginMemberCommand("mb2", "1234");

        // when -> then
        assertThatThrownBy(() -> memberLoginService.login(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}