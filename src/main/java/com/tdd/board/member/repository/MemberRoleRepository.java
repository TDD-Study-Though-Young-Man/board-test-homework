package com.tdd.board.member.repository;

import com.tdd.board.member.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
