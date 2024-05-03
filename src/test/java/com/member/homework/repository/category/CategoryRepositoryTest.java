package com.member.homework.repository.category;

import com.member.homework.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.dao.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    // 이게 필요한 테스트일까?
    @Test
    @DisplayName("새로운 카테고리를 생성할 수 있다.")
    void adminCreatesNewCategorySuccessfully() {
        // given - 상황 만들기
        Category category = Category.builder()
                .name("해외축구갤러리")
                .description("해축갤 만세")
                .build();

        // when - 동작
        Category savedCategory = categoryRepository.save(category);

        // then - 검증
        assertThat(savedCategory).extracting("name", "description")
                .containsExactlyInAnyOrder(
                        "해외축구갤러리",
                        "해축갤 만세"
                );
    }

    @Test
    @DisplayName("같은 이름의 카테고리는 생성할 수 없다.")
    void cannotMakeCategoryWithExistingName() {
        // given - 상황 만들기
        Category existingCategory = Category.of("해외축구갤러리", "해축갤 만세", null);
        Category newCategory = Category.of("해외축구갤러리", "다른 내용으로 만세", null);
        categoryRepository.save(existingCategory);

        // when, then
        assertThatThrownBy(() -> categoryRepository.save(newCategory))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("존재하는 모든 카테고리를 조회할 수 있어야 한다.")
    void findAllTest() {
        // given - 상황 만들기
        categoryRepository.saveAll(
                List.of(
                        Category.of("1", "1", null),
                        Category.of("2", "2", null),
                        Category.of("3", "3", null)
                )
        );

        // when - 동작
        List<Category> allCategories = categoryRepository.findAll();

        // then - 검증
        assertThat(allCategories).hasSize(3)
                .extracting("name", "description", "parent")
                .containsExactlyInAnyOrder(
                        tuple("1", "1", null),
                        tuple("2", "2", null),
                        tuple("3", "3", null)
                );
    }

    @Test
    @DisplayName("카테고리가 비어있는 경우, 빈 리스트를 보여준다.")
    void showsEmptyListWhenNothing() {
        // when - 동작
        List<Category> emptyList = categoryRepository.findAll();

        // then - 검증
        assertThat(emptyList).isEmpty();
    }

    @Test
    @DisplayName("특정 카테고리를 삭제할 수 있다.")
    void adminCanDeleteSpecificCategory() {
        // given - 상황 만들기
        Category categoryToBeDeleted = Category.of("1", "1", null);
        categoryRepository.save(categoryToBeDeleted);

        // when - 동작
        categoryRepository.delete(categoryToBeDeleted);
        Category unexistingCategory = categoryRepository.findByName("1");

        // then - 검증
        assertThat(unexistingCategory).isNull();
    }

}
