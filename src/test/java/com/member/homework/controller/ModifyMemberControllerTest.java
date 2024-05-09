package com.member.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.homework.controller.admin.ModifyMemberController;
import com.member.homework.dto.request.ModifyMemberCommand;
import com.member.homework.service.ModifyMemberService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static com.member.homework.exception.ErrorCode.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc(addFilters = false)
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
        ModifyMemberCommand request = modify("ljm", "12345", "이잼");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data['id']").value(request.getId()))
                .andExpect(jsonPath("$.data['password']").value(request.getPassword()))
                .andExpect(jsonPath("$.data['name']").value(request.getName()));
    }

    @Test
    void 회원을_수정하려면_아이디를_가져야_한다() throws Exception {

        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify(" ", "12345", "이잼");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['id']").value(NOT_BE_EMPTY_MEMBER_ID.getDetail()));
    }

    @Test
    void 회원을_수정하려면_비밀번호를_가져야_한다() throws Exception {

        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify("오랑우", " ", "이잼");

        //when //then

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['password']").value(NOT_BE_EMPTY_MEMBER_PASSWORD.getDetail()));
    }

    @Test
    void 회원을_수정하려면_이름을_가져야_한다() throws Exception {

        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify("오랑우", "탄입니다이", " ");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['name']").value(NOT_BE_EMPTY_MEMBER_NAME.getDetail()));
    }

    @Test
    void 회원을_수정하려면_아이디와_비밀번호를_가져야_한다() throws Exception {
        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify(" ", " ", "우탄이");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['id']").value(NOT_BE_EMPTY_MEMBER_ID.getDetail()))
                .andExpect(jsonPath("$.data['password']").value(NOT_BE_EMPTY_MEMBER_PASSWORD.getDetail()));

    }

    @Test
    void 회원을_수정하려면_아이디와_비밀번호와_이름을_가져야_한다() throws Exception {
        // given
        Long userId = 1L;
        ModifyMemberCommand request = modify(" ", " ", " ");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/users/{userId}", userId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['id']").value(NOT_BE_EMPTY_MEMBER_ID.getDetail()))
                .andExpect(jsonPath("$.data['password']").value(NOT_BE_EMPTY_MEMBER_PASSWORD.getDetail()))
                .andExpect(jsonPath("$.data['name']").value(NOT_BE_EMPTY_MEMBER_NAME.getDetail()));
    }

    private ModifyMemberCommand modify(String id, String password, String name) {
        return new ModifyMemberCommand(id, password, name);
    }
}