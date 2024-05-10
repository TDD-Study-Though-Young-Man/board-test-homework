package com.member.homework.repository.member;

import com.member.homework.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String id);
    boolean existsMemberByLoginId(String id);
}
