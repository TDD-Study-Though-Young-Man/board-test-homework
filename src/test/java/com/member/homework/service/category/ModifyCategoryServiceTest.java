package com.member.homework.service.category;

import com.member.homework.domain.*;
import com.member.homework.exception.*;
import com.member.homework.repository.category.*;
import com.member.homework.service.category.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ModifyCategoryServiceTest {

    @Autowired
    private ModifyCategoryService modifyCategoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리의 이름, 설명, 부모 카테고리를 수정한다.")
    void modifyCategoryNameDescParent() {
        // given - 상황 만들기
        Category modifyingChild = createCategory(1);
        Category parent = createCategory(2);
        categoryRepository.saveAllAndFlush(
                List.of(
                        modifyingChild,
                        parent
                )
        );
        ModifyCategoryServiceRequest modifyRequest = new ModifyCategoryServiceRequest(
                modifyingChild.getId(),
                "changed name",
                "changed description",
                parent.getId()
        );

        // when - 동작
        modifyCategoryService.modifyCategory(modifyRequest);
        Category modifiedCategory = categoryRepository.findById(modifyingChild.getId()).get();

        // then - 검증
        assertThat(modifiedCategory).isNotNull()
                .extracting("name", "description", "parent")
                .containsExactlyInAnyOrder(
                        "changed name",
                        "changed description",
                        parent
                );
    }

    @Test
    @DisplayName("존재하지 않는 카테고리를 수정하는 경우, 수정이 불가하다.")
    void cantModifyUnexistingCategory() {
        // given - 상황 만들기
        categoryRepository.saveAllAndFlush(List.of(
                createCategory(1),
                createCategory(2),
                createCategory(3)
        ));
        long unexistingId = categoryRepository.findMaxCategoryId() + 1;
        ModifyCategoryServiceRequest modifyRequest = new ModifyCategoryServiceRequest(
                unexistingId,
                "changed name",
                "changed description",
                -1
        );

        // when - 동작, then - 검증
        assertThatThrownBy(() -> modifyCategoryService.modifyCategory(modifyRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 카테고리 ID 입니다.");

    }

    @Test
    @DisplayName("존재하지 않는 id 의 부모 카테고리로 설정은 불가능하다.")
    void cnatModifyParentIdWithUnexistingId() {
        // given - 상황 만들기
        Category category1 = createCategory(1);
        Category category2 = createCategory(2);
        Category category3 = createCategory(3);
        categoryRepository.saveAllAndFlush(List.of(
                category1,
                category2,
                category3
        ));
        long unexistingId = categoryRepository.findMaxCategoryId() + 1;
        ModifyCategoryServiceRequest modifyRequest = new ModifyCategoryServiceRequest(
                category1.getId(),
                "changed name",
                "changed description",
                unexistingId
        );

        // when - 동작, then - 검증
        assertThatThrownBy(() -> modifyCategoryService.modifyCategory(modifyRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 카테고리 ID 입니다.");
    }

    private Category createCategory(int num) {
        return Category.of("test name" + num, "test desc" + num, null);
    }
}