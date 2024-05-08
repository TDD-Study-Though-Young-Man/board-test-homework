package com.tdd.board.category.service;

import com.tdd.board.category.domain.Category;
import com.tdd.board.exception.*;
import com.tdd.board.category.repository.CategoryRepository;
import com.tdd.board.category.service.RemoveCategoryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RemoveCategoryServiceTest {

    @Autowired
    private RemoveCategoryService removeCategoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("아무 게시글이 없는 카테고리의 경우, 삭제할 수 있다.")
    void canDeleteCategoryWithAnyPosts() {
        // given - 상황 만들기
        Category category = createCategory(1);
        categoryRepository.saveAndFlush(category);

        // when - 동작
        categoryRepository.delete(category);
        List<Category> categories = categoryRepository.findAll();

        // then - 검증
        assertThat(categories).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 카테고리의 ID 로 삭제할 수 없다.")
    void cantDeleteWithUnexistingCategoryId() {
        // given - 상황 만들기
        Category category1 = createCategory(1);
        Category category2 = createCategory(2);
        Category category3 = createCategory(3);
        categoryRepository.saveAllAndFlush(List.of(
                category1,
                category2,
                category3
        ));

        // when - 동작
        Long unexistingId = categoryRepository.findMaxCategoryId() + 1;

        // then - 검증
        assertThatThrownBy(() -> removeCategoryService.removeCategory(unexistingId))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 카테고리 ID 입니다.");
    }

    @Test
    @DisplayName("게시글이 존재하는 카테고리는 삭제가 되지 않는다.")
    void cantDeleteCategoryWithPosts() {
        // given - 상황 만들기


        // when - 동작

        // then - 검증
    }


    private static Category createCategory(int num) {
        return Category.of("test name" + num, "test desc" + num, null);
    }
}