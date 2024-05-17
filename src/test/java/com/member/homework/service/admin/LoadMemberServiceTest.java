package com.member.homework.service.admin;

import com.member.homework.domain.Member;
import com.member.homework.dto.response.MemberDto;
import com.member.homework.service.admin.LoadMemberService;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class LoadMemberServiceTest {

    @Autowired
    private LoadMemberService loadMemberService;

    @Autowired
    private TestUtil testUtil;


    @Test
    void 관리자는_회원을_전체조회_할_수_있어야_한다() {
        // given
        List<Member> memberList = List.of(
                Member.of("mb1", "1234", "01"),
                Member.of("mb2", "5678", "02"),
                Member.of("궁햄", "112", "03")
        );

        testUtil.saveAllMembers(memberList);

        // when
        List<MemberDto> findMemberList = loadMemberService.loadAllMembers();

        // then
        assertThat(findMemberList).hasSize(3)
                .extracting("loginId", "name")
                .containsExactlyInAnyOrder(
                    tuple("mb1", "01"),
                    tuple("mb2", "02"),
                    tuple("궁햄", "03")
                );
    }
}