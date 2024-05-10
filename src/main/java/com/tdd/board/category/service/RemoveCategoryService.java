package com.tdd.board.category.service;


import com.tdd.board.category.domain.Category;
import com.tdd.board.exception.*;
import com.tdd.board.category.repository.CategoryRepository;
import com.tdd.board.repository.category.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RemoveCategoryService {

    private final CategoryRepository categoryRepository;

    public void removeCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND_BY_ID);
        }

        Category categoryToBeDeleted = optionalCategory.get();
        categoryRepository.delete(categoryToBeDeleted);
    }
}
