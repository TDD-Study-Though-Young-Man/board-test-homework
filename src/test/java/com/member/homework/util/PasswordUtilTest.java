package com.member.homework.util;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PasswordUtilTest {

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호를 평문으로 입력하면 암호화 할 수 있어야 한다.")
    void encodePasswordTest() {
        // given
        String rawPassword = "1234";

        // when
        String encryptedPassword = passwordUtil.encodePassword(rawPassword);

        // then
        assertThat(passwordEncoder.matches(rawPassword, encryptedPassword))
                .isTrue();
    }

    @Test
    @DisplayName("입력한 비밀번호와 암호화 한 비밀번호가 다르면 인증에 실패해야 한다.")
    void checkPasswordTest() {
        // given
        String rawPassword = "1234";
        String encryptedPassword = passwordUtil.encodePassword("1233");


        // when -> then
        assertThatThrownBy(() -> passwordUtil.checkPassword(rawPassword, encryptedPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 또는 비밀번호가 다릅니다.");

    }

}