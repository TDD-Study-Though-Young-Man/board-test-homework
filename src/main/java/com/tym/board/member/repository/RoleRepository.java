package com.tym.board.member.repository;

import com.tym.board.member.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByRoleNameIn(List<String> roleName);
}
