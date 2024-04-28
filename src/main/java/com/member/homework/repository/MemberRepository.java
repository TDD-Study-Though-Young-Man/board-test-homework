package com.member.homework.repository;

import com.member.homework.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);
    boolean existsMemberById(String id);
}
