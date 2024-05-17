package com.member.homework.controller.admin;

import com.member.homework.config.SecurityConfig;
import com.member.homework.controller.admin.LoadMemberController;
import com.member.homework.dto.response.MemberDto;
import com.member.homework.service.admin.LoadMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(LoadMemberController.class)
class LoadMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadMemberService loadMemberService;

    @Test
    void 관리자는_모든_회원을_조회할_수_있다() throws Exception {
        // given
        List<MemberDto> dtoList = List.of(
                new MemberDto("mb1", "회원1"),
                new MemberDto("mb2", "회원2"),
                new MemberDto("mb3", "회원3")
        );

        when(loadMemberService.loadAllMembers()).thenReturn(dtoList);

        // when
        ResultActions actions = mockMvc.perform(get("/api/admin/users")
                .contentType(
                        MediaType.APPLICATION_JSON)
                        .header("Authentication", "ADMIN")
                ).andDo(print());

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value("mb1"))
                .andExpect(jsonPath("$.data[1].id").value("mb2"))
                .andExpect(jsonPath("$.data[2].id").value("mb3"))
                .andExpect(jsonPath("$.data[0].name").value("회원1"))
                .andExpect(jsonPath("$.data[1].name").value("회원2"))
                .andExpect(jsonPath("$.data[2].name").value("회원3"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    void 관리자_권한이_없는_경우_회원을_조회할_수_없다() throws Exception {
        // when -> then
        mockMvc.perform(get("/api/admin/users")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .header("Authentication", "MEMBER")
                ).andDo(print())
                .andExpect(status().isForbidden());
    }

}