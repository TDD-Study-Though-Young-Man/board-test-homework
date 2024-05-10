package com.member.homework.util;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PasswordUtilTest {

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void 비밀번호를_평문으로_입력하면_암호화_할_수_있어야_한다() {
        // given
        String rawPassword = "1234";

        // when
        String encryptedPassword = passwordUtil.encodePassword(rawPassword);

        // then
        assertThat(passwordEncoder.matches(rawPassword, encryptedPassword))
                .isTrue();
    }

    @Test
    void 입력한_비밀번호와_암호화_한_비밀번호가_다르면_인증에_실패해야_한다() {
        // given
        String rawPassword = "1234";
        String encryptedPassword = passwordUtil.encodePassword("1233");


        // when -> then
        assertThatThrownBy(() -> passwordUtil.checkPassword(rawPassword, encryptedPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 또는 비밀번호가 다릅니다.");

    }

}