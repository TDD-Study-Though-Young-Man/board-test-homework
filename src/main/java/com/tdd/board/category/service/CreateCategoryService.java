package com.tdd.board.category.service;

import com.tdd.board.category.domain.Category;
import com.tdd.board.exception.*;
import com.tdd.board.category.repository.CategoryRepository;
import com.tdd.board.category.dto.CreateCategoryServiceRequest;
import com.tdd.board.repository.category.*;
import com.tdd.board.service.category.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCategoryService {

    private final CategoryRepository categoryRepository;

    public void createCategory(CreateCategoryServiceRequest createCategoryServiceRequest) {
        Long parentId = createCategoryServiceRequest.parentId();
        checkParentId(parentId);

        categoryRepository.save(Category.of(
                createCategoryServiceRequest.name(),
                createCategoryServiceRequest.description(),
                null
        ));
    }

    private void checkParentId(Long parentId) {
        if (parentId != -1 && categoryRepository.findById(parentId).isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND_BY_ID);
        }
    }
}
