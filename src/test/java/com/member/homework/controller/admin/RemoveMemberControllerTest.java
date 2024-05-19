package com.member.homework.controller.admin;

import com.member.homework.controller.admin.RemoveMemberController;
import com.member.homework.service.admin.RemoveMemberService;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.nio.charset.StandardCharsets;
import static com.member.homework.exception.ErrorCode.PARAMETER_INVALID;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = RemoveMemberController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RemoveMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RemoveMemberService removeMemberService;

    @Test
    void 회원을_삭제한다() throws Exception {

        // given
        Long userId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.data").value(userId))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()));
    }

    @Test
    void 회원을_삭제하려면_0이상의_아이디를_가져야_한다() throws Exception {

        // given
        Long userId = -1L;

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8));

        // then
        resultActions.andDo(print())
                .andExpect(jsonPath("$.code").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data").value(PARAMETER_INVALID.getDetail()));

        verify(removeMemberService, never()).removeMember(userId);
    }
}