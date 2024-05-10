package com.member.homework.repository;

import com.member.homework.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 가입된_회원을_전체_조회할_수_있어야_한다() {
        // given
        memberRepository.saveAll(
                List.of(
                   Member.of("member1", "1234", "01"),
                   Member.of("member2", "5678", "02"),
                   Member.of("member3", "1111", "03")
                ));

        // when
        List<Member> memberList = memberRepository.findAll();

        // then
        assertThat(memberList).hasSize(3)
                .extracting("loginId", "password", "name")
                .containsExactlyInAnyOrder(
                        tuple("member1", "1234", "01"),
                        tuple("member2", "5678", "02"),
                        tuple("member3", "1111", "03")
                );
    }

    @Test
    void 회원의_고유_ID로_회원을_조회할_수_있어야_한다() {
        // given
        String loginId = "javajunsuk123";
        Member member = Member.of(loginId, "devcamp", "01");
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findByLoginId(loginId)
                .orElseThrow();

        // then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void 회원의_ID로_회원_존재_여부를_알_수_있어야_한다() {
        // given
        memberRepository.save(Member.of("mb1", "1234", "궁햄"));

        // when
        boolean successResult = memberRepository.existsMemberByLoginId("mb1");
        boolean failureResult = memberRepository.existsMemberByLoginId("mb2");

        // then
        assertThat(successResult).isTrue();
        assertThat(failureResult).isFalse();
    }

    @Test
    void 회원의_고유_ID_로_회원을_삭제할_수_있어야_한다() {
        // given
        Member member = memberRepository.save(Member.of("mb1", "1234", "궁햄"));

        // when
        memberRepository.deleteById(member.getMemberId());

        // then
        assertThatThrownBy(() -> memberRepository.findById(member.getMemberId()).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }



}