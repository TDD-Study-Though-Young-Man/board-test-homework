package com.member.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.homework.config.SecurityConfig;
import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.service.AdminLoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = AdminLoginController.class)
class AdminLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminLoginService adminLoginService;

    @Test
    @DisplayName("관리자는 관리자 페이지에 로그인 할 수 있다.")
    void loginTest() throws Exception {
        // given
        LoginMemberCommand loginMemberCommand = createLoginMemberCommand("id", "password");
        when(adminLoginService.login(any(LoginMemberCommand.class))).thenReturn("jwttoken");

        // when -> then
        mockMvc.perform(post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginMemberCommand)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("jwttoken"))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    @DisplayName("관리자 페이지 로그인시 아이디는 비어있을 수 없다.")
    void loginTestWithBlankId() throws Exception {
        // given
        LoginMemberCommand loginMemberCommand = createLoginMemberCommand(" ", "password");
        when(adminLoginService.login(any(LoginMemberCommand.class))).thenReturn("jwttoken");

        // when -> then
        mockMvc.perform(post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginMemberCommand)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("아이디는 비어있을 수 없습니다."));
    }

    @Test
    @DisplayName("관리자 페이지 로그인시 비밀번호는 비어있을 수 없다.")
    void loginTestWithBlankPassword() throws Exception {
        // given
        LoginMemberCommand loginMemberCommand = createLoginMemberCommand("id", " ");
        when(adminLoginService.login(any(LoginMemberCommand.class))).thenReturn("jwttoken");

        // when -> then
        mockMvc.perform(post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginMemberCommand)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("비밀번호는 비어있을 수 없습니다."));
    }

    private LoginMemberCommand createLoginMemberCommand(String id, String password) {
        return new LoginMemberCommand(id, password);
    }

}