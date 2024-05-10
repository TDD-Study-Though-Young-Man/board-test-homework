package com.member.homework.repository;

import com.member.homework.domain.Role;
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
    void 권한의_이름들로_권한을_찾을_수_있어야_한다() {
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