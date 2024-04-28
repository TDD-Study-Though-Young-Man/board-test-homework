package com.member.homework.repository;

import com.member.homework.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByRoleNameIn(List<String> roleName);
}
