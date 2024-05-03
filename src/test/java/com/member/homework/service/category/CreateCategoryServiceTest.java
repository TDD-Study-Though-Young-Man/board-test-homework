package com.member.homework.service.category;

import com.member.homework.domain.*;
import com.member.homework.exception.*;
import com.member.homework.repository.category.*;
import com.member.homework.service.category.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CreateCategoryServiceTest {

    @Autowired
    private CreateCategoryService createCategoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("관리자는 새로운 카테고리를 생성할 수 있다.")
    void adminCanCreateNewCategory() {
        // given - 상황 만들기
        CreateCategoryServiceRequest createCategoryServiceRequest
                = new CreateCategoryServiceRequest(
                "name",
                "description",
                -1L
        );

        // when - 동작
        createCategoryService.createCategory(createCategoryServiceRequest);
        categoryRepository.flush();
        Category createdCategory = categoryRepository.findByName("name");

        // then - 검증
        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory)
                .extracting("name", "desription", "parent")
                .containsExactlyInAnyOrder("name", "description", null);
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 ID 로 대분류의 ID 를 설정할 수 없다.")
    void cannotSetParentIdWithUnexistingId() {
        // given - 상황 만들기
        Category existingCategory = Category.of("존재하는 카테고리", "존재하는 카테고리 설명", null);
        categoryRepository.save(existingCategory);
        categoryRepository.flush();

        Long existingCategoryId = categoryRepository.findByName("존재하는 카테고리").getId();
        CreateCategoryServiceRequest createCategoryServiceRequest
                = new CreateCategoryServiceRequest(
                "추가하는 카테고리",
                "추가하는 카테고리 설명",
                existingCategoryId
        );

        // when - 동작, then
        assertThatThrownBy(() -> createCategoryService.createCategory(createCategoryServiceRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 카테고리 ID 입니다.");
    }

}