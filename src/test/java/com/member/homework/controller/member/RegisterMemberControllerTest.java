package com.member.homework.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.homework.config.SecurityConfig;
import com.member.homework.dto.request.RegisterMemberCommand;
import com.member.homework.service.member.RegisterMemberService;
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
import java.nio.charset.StandardCharsets;
import static com.member.homework.exception.ErrorCode.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
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
        RegisterMemberCommand request = create("memberId", "memberPassword", "memberName");

        //when
        then(registerMemberService.register(request)).isInstanceOf(Long.class);

        ResultActions resultActions = mockMvc.perform(post("/api/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .header(HttpHeaders.AUTHORIZATION, "ADMIN")
                .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions
                .andExpect(jsonPath("$.data").isNumber())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()));
    }

    @Test
    void 회원가입을_하려면_아이디를_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create(" ", "memberPassword", "memberName");

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['loginId']").value(ID_CANNOT_BE_EMPTY.getDetail()));

    }

    @Test
    void 회원가입을_하려면_비밀번호를_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create("memberId", " ", "memberName");

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/admin/users")
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
    void 회원가입을_하려면_이름을_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create("memberId", "memberName", "  ");

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['name']").value(NAME_CANNOT_BE_EMPTY.getDetail()));
    }

    @Test
    void 회원가입을_하려면_아이디와_비밀번호를_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create(" ", " ", "memberName");

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['loginId']").value(ID_CANNOT_BE_EMPTY.getDetail()))
                .andExpect(jsonPath("$.data['password']").value(PASSWORD_CANNOT_BE_EMPTY.getDetail()));
    }

    @Test
    void 회원가입을_하려면_아이디와_비밀번호와_이름을_가져야_한다() throws Exception {

        // given
        RegisterMemberCommand request = create(" ", " ", " ");

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "ADMIN"));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data['loginId']").value(ID_CANNOT_BE_EMPTY.getDetail()))
                .andExpect(jsonPath("$.data['name']").value(NAME_CANNOT_BE_EMPTY.getDetail()))
                .andExpect(jsonPath("$.data['password']").value(PASSWORD_CANNOT_BE_EMPTY.getDetail()));
    }

    private RegisterMemberCommand create(String id, String password, String name) {
        return new RegisterMemberCommand(id, password, name);
    }
}