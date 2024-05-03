package com.member.homework.service.category;

import com.member.homework.domain.*;
import com.member.homework.exception.*;
import com.member.homework.repository.category.*;
import com.member.homework.service.category.dto.*;
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
        if (parentId != -1 && categoryRepository.findById(parentId).isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND_BY_ID);
        }

        categoryRepository.save(Category.of(
                createCategoryServiceRequest.name(),
                createCategoryServiceRequest.description(),
                null
        ));
    }
}
