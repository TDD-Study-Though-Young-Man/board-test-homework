package com.member.homework.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.homework.config.SecurityConfig;
import com.member.homework.dto.request.ModifyMemberCommand;
import com.member.homework.service.member.ModifyMemberService;
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
import static com.member.homework.exception.ErrorCode.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = ModifyMemberController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ModifyMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModifyMemberService modifyMemberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 회원을_수정할_수_있다() throws Exception {

        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify("memberId", "memberPassword", "memberName");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data['id']").value(request.id()))
                .andExpect(jsonPath("$.data['password']").value(request.password()))
                .andExpect(jsonPath("$.data['name']").value(request.name()));
    }

    @Test
    void 회원을_수정하려면_아이디를_가져야_한다() throws Exception {

        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify(" ", "memberPassword", "memberName");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['id']").value(ID_CANNOT_BE_EMPTY.getDetail()));
    }

    @Test
    void 회원을_수정하려면_비밀번호를_가져야_한다() throws Exception {

        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify("memberId", " ", "memberName");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['password']").value(PASSWORD_CANNOT_BE_EMPTY.getDetail()));
    }

    @Test
    void 회원을_수정하려면_이름을_가져야_한다() throws Exception {

        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify("memberId", "memberPassword", " ");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));
        // then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['name']").value(NAME_CANNOT_BE_EMPTY.getDetail()));
    }

    @Test
    void 회원을_수정하려면_아이디와_비밀번호를_가져야_한다() throws Exception {
        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify(" ", " ", "memberName");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['id']").value(ID_CANNOT_BE_EMPTY.getDetail()))
                .andExpect(jsonPath("$.data['password']").value(PASSWORD_CANNOT_BE_EMPTY.getDetail()));

    }

    @Test
    void 회원을_수정하려면_아이디와_비밀번호와_이름을_가져야_한다() throws Exception {
        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify(" ", " ", " ");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['id']").value(ID_CANNOT_BE_EMPTY.getDetail()))
                .andExpect(jsonPath("$.data['password']").value(PASSWORD_CANNOT_BE_EMPTY.getDetail()))
                .andExpect(jsonPath("$.data['name']").value(NAME_CANNOT_BE_EMPTY.getDetail()));
    }

    private ModifyMemberCommand modify(String id, String password, String name) {
        return new ModifyMemberCommand(id, password, name);
    }
}