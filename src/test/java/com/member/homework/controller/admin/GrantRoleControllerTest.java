package com.member.homework.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.homework.config.SecurityConfig;
import com.member.homework.dto.request.GrantRoleCommand;
import com.member.homework.service.admin.GrantRoleService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static com.member.homework.exception.ErrorCode.PARAMETER_INVALID;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = GrantRoleController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GrantRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrantRoleService grantRoleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 사용자에게_권한을_부여한다() throws Exception {

        // given
        Long userId = 1L;

        GrantRoleCommand grantRoleCommand = new GrantRoleCommand(List.of("MEMBER"));

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users/{userId}/roles", userId)
                .content(objectMapper.writeValueAsString(grantRoleCommand))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "ADMIN")
                .characterEncoding(StandardCharsets.UTF_8));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isNumber())
                .andExpect(jsonPath("$.data").value(userId))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()));
    }

    @Test
    void 사용자에게_권한을_부여하려면_0이상의_아이디를_가져야_한다() throws Exception {

        // given
        Long userId = -1L;

        GrantRoleCommand grantRoleCommand = new GrantRoleCommand(List.of("MEMBER"));


        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users/{userId}/roles", userId)
                .content(objectMapper.writeValueAsString(grantRoleCommand))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "ADMIN")
                .characterEncoding(StandardCharsets.UTF_8));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.code").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data").value(PARAMETER_INVALID.getDetail()));

        verify(grantRoleService, never()).grantRoleToMember(userId, grantRoleCommand.roleList());

    }
}