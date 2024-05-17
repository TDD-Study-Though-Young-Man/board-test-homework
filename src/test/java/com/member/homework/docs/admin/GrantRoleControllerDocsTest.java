package com.member.homework.docs.admin;

import com.member.homework.controller.admin.GrantRoleController;
import com.member.homework.docs.RestDocsSupport;
import com.member.homework.dto.request.GrantRoleCommand;
import com.member.homework.service.admin.GrantRoleService;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.mockito.Mockito.mock;

class GrantRoleControllerDocsTest extends RestDocsSupport {

    private final GrantRoleService grantRoleService = mock(GrantRoleService.class);

    @Override
    protected Object initController() {
        return new GrantRoleController(grantRoleService);
    }

    @Test
    @DisplayName("회원 권한 부여 API")
    void grantRoleToMember() throws Exception {
        // given
        GrantRoleCommand grantRoleCommand = new GrantRoleCommand(List.of("ADMIN", "MEMBER", "SUPER_ADMIN"));
        Long userId = 1L;

        // when
        ResultActions actions = mockMvc.perform(
                        post("/api/admin/users/{userId}/roles", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(grantRoleCommand))
                        .header(HttpHeaders.AUTHORIZATION, "ADMIN"))
                .andDo(print());

        // then -> 간단한 검증과 문서화
        actions.andExpect(status().isOk())
                .andDo(document("admin-grant-role",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("회원 권한")
                        ),
                        pathParameters(
                                parameterWithName("userId").description("권한을 부여할 회원의 PK")
                        ),
                        requestFields(
                                fieldWithPath("roleList").type(JsonFieldType.ARRAY)
                                        .description("회원에게 부여할 권한 배열")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메시지"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 상태 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("응답 상태 코드 메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER)
                                        .description("응답 데이터")
                        )));
    }
}
