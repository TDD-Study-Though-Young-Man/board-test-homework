//package com.member.homework.service.category;
//
//import com.member.homework.domain.*;
//import com.member.homework.repository.category.*;
//import com.member.homework.service.category.dto.*;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.boot.test.context.*;
//import org.springframework.transaction.annotation.*;
//
//import java.util.*;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.groups.Tuple.tuple;
//
//
//@SpringBootTest
//@Transactional
//class LoadCategoryServiceTest {
//
//    @Autowired
//    private LoadCategoryService loadCategoryService;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Test
//    @DisplayName("관리자가 모든 카테고리를 조회할 수 있다.")
//    void canLoadAllCategories() {
//        // given - 상황 만들기
//        Category category1 = createCategory(1);
//        Category category2 = createCategory(2);
//        Category category3 = createCategory(3);
//        categoryRepository.saveAll(List.of(
//                category1,
//                category2,
//                category3
//        ));
//
//        // when - 동작
//        List<Category> categories = loadCategoryService.loadAllCategories();
//
//        // then - 검증
//        assertThat(categories).hasSize(3)
//                .extracting("name", "description")
//                .containsExactlyInAnyOrder(
//                        tuple("test name1", "test desc1"),
//                        tuple("test name2", "test desc2"),
//                        tuple("test name3", "test desc3")
//                );
//
//    }
//
//    @Test
//    @DisplayName("카테고리가 없으면 비어있는 리스트를 보여준다")
//    void showEmptyListWhenNoCategories() {
//        // given - 상황 만들기
//
//        // when - 동작
//        List<Category> categories = loadCategoryService.loadAllCategories();
//
//        // then - 검증
//        assertThat(categories).hasSize(0);
//    }
//
//    private static Category createCategory(int num) {
//        return Category.of("test name" + num, "test desc" + num, null);
//    }
//
//    private CreateCategoryServiceRequest getCreateCommand(String testName, String testDesc, long l) {
//        return new CreateCategoryServiceRequest(testName, testDesc, l);
//    }
//
//}
