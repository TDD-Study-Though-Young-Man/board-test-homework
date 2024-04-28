package com.member.homework.repository;

import com.member.homework.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("권한의 이름들로 권한을 찾을 수 있어야 한다.")
    void findAllByRoleNameInTest() {
        // given
        roleRepository.saveAll(List.of(
                Role.of("ADMIN"),
                Role.of("MEMBER"),
                Role.of("YEAH")
        ));

        // when
        List<Role> roleList = roleRepository.findAllByRoleNameIn(List.of("ADMIN", "MEMBER", "YEAH"));

        // then
        assertThat(roleList).hasSize(3)
                .extracting("roleName")
                .contains("ADMIN", "MEMBER", "YEAH");
    }

}