package com.member.homework.docs.admin;

import com.member.homework.controller.admin.LoadMemberController;
import com.member.homework.docs.RestDocsSupport;
import com.member.homework.dto.response.MemberDto;
import com.member.homework.service.admin.LoadMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoadMemberControllerDocsTest extends RestDocsSupport {

    private final LoadMemberService loadMemberService = mock(LoadMemberService.class);

    @Override
    protected Object initController() {
        return new LoadMemberController(loadMemberService);
    }

    @Test
    @DisplayName("회원 전체 조회 API")
    void loadAllMembers() throws Exception {
        // given
        given(loadMemberService.loadAllMembers()).willReturn(
                List.of(new MemberDto("회원1", "회원이름1"),
                        new MemberDto("회원2", "회원이름2"))
        );

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "MEMBER"))
                .andDo(print());

        // then
        actions.andExpect(status().isOk())
                .andDo(document("admin-load-members",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("회원 권한")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메시지"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 상태 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("응답 상태 코드 메시지"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY)
                                        .description("응답 데이터"),
                                fieldWithPath("data[].loginId").type(JsonFieldType.STRING)
                                        .description("회원 로그인 ID"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING)
                                        .description("회원 이름")
                        )));
    }
}
