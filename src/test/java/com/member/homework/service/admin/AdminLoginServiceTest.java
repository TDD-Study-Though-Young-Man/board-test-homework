package com.member.homework.service.admin;

import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.service.admin.AdminLoginService;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class AdminLoginServiceTest {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private TestUtil testUtil;

    @Test
    void 관리자_권한이_있는_사용자는_로그인에_성공해야_한다() {
        // given
        String id = "mb1";
        String password = "1234";

        testUtil.createMember(id, password, "ADMIN", "궁햄");
        LoginMemberCommand command = testUtil.createLoginMemberCommand("mb1", "1234");

        // when
        String result = adminLoginService.login(command);

        // then
        assertThat(result).isEqualTo("jwttoken");
    }

    @Test
    void 관리자_권한이_없는_사용자는_로그인에_실패해야_한다() {
        // given
        String id = "mb1";
        String password = "1234";

        testUtil.createMember(id, password, "MEMBER", "궁햄");
        LoginMemberCommand command = testUtil.createLoginMemberCommand(id, password);

        // when -> then
        assertThatThrownBy(() -> adminLoginService.login(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("관리자 권한이 없습니다. 관리자 페이지에 진입할 수 없습니다.");
    }

    @Test
    void 회원은_존재하지만_아이디와_비밀번호가_다른_경우_로그인이_실패한다() {
        // given
        String id = "mb1";

        testUtil.createMember(id, "1234", "MEMBER", "궁햄");
        LoginMemberCommand command = testUtil.createLoginMemberCommand(id, "1233");

        // when -> then
        assertThatThrownBy(() -> adminLoginService.login(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 또는 비밀번호가 다릅니다.");
    }

    @Test
    void 로그인_시도한_아이디가_존재하지_않는_아이디라면_로그인이_실패한다() {
        // given
        String password = "1234";

        testUtil.createMember("mb1", password, "MEMBER", "궁햄2");
        LoginMemberCommand command = testUtil.createLoginMemberCommand("궁햄", "1234");

        // when -> then
        assertThatThrownBy(() -> adminLoginService.login(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}