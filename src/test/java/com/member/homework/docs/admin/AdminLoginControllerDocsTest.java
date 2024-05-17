package com.member.homework.docs.admin;

import com.member.homework.controller.admin.AdminLoginController;
import com.member.homework.docs.RestDocsSupport;
import com.member.homework.dto.request.LoginMemberCommand;
import com.member.homework.service.admin.AdminLoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AdminLoginControllerDocsTest extends RestDocsSupport {

    private final AdminLoginService adminLoginService = mock(AdminLoginService.class);

    @Override
    protected Object initController() {
        return new AdminLoginController(adminLoginService);
    }

    @Test
    @DisplayName("관리자 로그인 API")
    void adminLogin() throws Exception {
        // given
        LoginMemberCommand loginMemberCommand = new LoginMemberCommand("id", "password");
        given(adminLoginService.login(any(LoginMemberCommand.class))).willReturn("jwtToken");

        // when
        ResultActions actions = mockMvc.perform(
                post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginMemberCommand)))
                .andDo(print());

        // then -> 간단한 검증과 문서화
        actions.andExpect(status().isOk())
                .andDo(document("admin-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING)
                                        .description("로그인 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("로그인 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메시지"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 상태 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("응답 상태 코드 메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING)
                                        .description("응답 데이터")
                        )));

    }
}
