package com.member.homework.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.homework.config.SecurityConfig;
import com.member.homework.controller.admin.AdminLoginController;
import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.service.admin.AdminLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void 관리자는_관리자_페이지에_로그인_할_수_있다() throws Exception {
        // given
        LoginMemberCommand loginMemberCommand = createLoginMemberCommand("id", "password");
        when(adminLoginService.login(any(LoginMemberCommand.class))).thenReturn("jwttoken");

        // when
        ResultActions actions = mockMvc.perform(post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginMemberCommand)))
                .andDo(print());


        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("jwttoken"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    void 관리자_페이지_로그인시_아이디는_비어있을_수_없다() throws Exception {
        // given
        LoginMemberCommand loginMemberCommand = createLoginMemberCommand(" ", "password");
        when(adminLoginService.login(any(LoginMemberCommand.class))).thenReturn("jwttoken");

        // when
        ResultActions actions = mockMvc.perform(post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginMemberCommand)))
                .andDo(print());


        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data['loginId']").value("아이디는 비어있을 수 없습니다."))
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void 관리자_페이지_로그인시_비밀번호는_비어있을_수_없다() throws Exception {
        // given
        LoginMemberCommand loginMemberCommand = createLoginMemberCommand("id", " ");
        when(adminLoginService.login(any(LoginMemberCommand.class))).thenReturn("jwttoken");

        // when
        ResultActions actions = mockMvc.perform(post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginMemberCommand)))
                .andDo(print());

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data['password']").value("비밀번호는 비어있을 수 없습니다."))
                .andExpect(jsonPath("$.code").value(400));
    }

    private LoginMemberCommand createLoginMemberCommand(String id, String password) {
        return new LoginMemberCommand(id, password);
    }

}