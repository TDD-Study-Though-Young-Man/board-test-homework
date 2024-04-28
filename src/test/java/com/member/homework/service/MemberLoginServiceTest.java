package com.member.homework.service;

import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("사용자는 게시판 서비스에 로그인 할 수 있어야 한다.")
    void loginTest() {
        // given
        testUtil.createMember("mb1", "1234", "MEMBER", "궁햄");
        LoginMemberCommand command = new LoginMemberCommand("mb1", "1234");

        // when
        String result = memberLoginService.login(command);

        // then
        assertThat(result).isEqualTo("jwttoken");
    }

    @Test
    @DisplayName("로그인 시도한 아이디가 존재하지 않는 아이디라면 로그인이 실패한다.")
    void isNotExistsMemberTest() {
        // given
        testUtil.createMember("mb1", "1234", "MEMBER", "궁햄");
        LoginMemberCommand command = new LoginMemberCommand("mb2", "1234");

        // when -> then
        assertThatThrownBy(() -> memberLoginService.login(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}