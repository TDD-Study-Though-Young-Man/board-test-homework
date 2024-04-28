package com.member.homework.service;

import com.member.homework.domain.Member;
import com.member.homework.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class LoadMemberServiceTest {

    @Autowired
    private LoadMemberService loadMemberService;

    @Autowired
    private TestUtil testUtil;


    @Test
    @DisplayName("관리자는 회원을 전체조회 할 수 있어야 한다.")
    void loadAllMembersTest() {
        // given
        List<Member> memberList = List.of(
                Member.of("mb1", "1234", "01"),
                Member.of("mb2", "5678", "02"),
                Member.of("궁햄", "112", "03")
        );

        testUtil.saveAllMembers(memberList);

        // when
        List<Member> findMemberList = loadMemberService.loadAllMembers();

        // then
        assertThat(findMemberList).hasSize(3)
                .extracting("id", "password", "name")
                .containsExactlyInAnyOrder(
                    tuple("mb1", "1234", "01"),
                    tuple("mb2", "5678", "02"),
                    tuple("궁햄", "112", "03")
                );
    }
}