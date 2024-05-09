package com.member.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.homework.controller.admin.RegisterMemberController;
import com.member.homework.dto.request.RegisterMemberCommand;
import com.member.homework.service.RegisterMemberService;
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
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = RegisterMemberController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RegisterMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterMemberService registerMemberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 회원가입을_할_수_있다() throws Exception {

        // given
        RegisterMemberCommand request = create("123", "qqq", "qqq");

        //when //then
        then(registerMemberService.register(request)).isInstanceOf(Number.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.data").isNumber())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()));
    }

    @Test
    void 회원가입을_하려면_아이디를_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create(" ", "111", "ok");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['id']").value(NOT_BE_EMPTY_MEMBER_ID.getDetail()));

    }

    @Test
    void 회원가입을_하려면_비밀번호를_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create("khm", " ", "ok");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['password']").value(NOT_BE_EMPTY_MEMBER_PASSWORD.getDetail()));
    }

    @Test
    void 회원가입을_하려면_이름을_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create("khm", "be", "  ");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['name']").value(NOT_BE_EMPTY_MEMBER_NAME.getDetail()));
    }

    @Test
    void 회원가입을_하려면_아이디와_비밀번호를_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create(" ", " ", "우간다");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users")
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
    void 회원가입을_하려면_아이디와_비밀번호와_이름을_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create(" ", " ", " ");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['id']").value(NOT_BE_EMPTY_MEMBER_ID.getDetail()))
                .andExpect(jsonPath("$.data['name']").value(NOT_BE_EMPTY_MEMBER_NAME.getDetail()))
                .andExpect(jsonPath("$.data['password']").value(NOT_BE_EMPTY_MEMBER_PASSWORD.getDetail()));
    }

    private RegisterMemberCommand create(String id, String password, String name) {
        return new RegisterMemberCommand(id, password, name);
    }
    /*
    @Test
    void 한_데이터에_두_가지_이상의_유효성_검증이_필요한_경우() throws Exception {

        // given
        RegisterMemberCommand request = create("", "be", "");

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data['name']").value("회원 이름은 필수입니다."))
                .andExpect(jsonPath("$.data['id']").isArray())
                .andExpect(jsonPath("$.data['id']").value(Matchers.containsInAnyOrder("회원 아이디가 공백이면 안됩니다.","회원 아이디는 필수입니다.")));
    }
     */
}