package com.tdd.board.member.repository;

import com.tdd.board.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);
    boolean existsMemberById(String id);
}
