package com.member.homework.service.category;

import com.member.homework.domain.*;
import com.member.homework.exception.*;
import com.member.homework.repository.category.*;
import com.member.homework.service.category.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyCategoryService {

    private final CategoryRepository categoryRepository;

    public void modifyCategory(ModifyCategoryServiceRequest modifyCommand) {
        Optional<Category> optionalModifyingCategory = categoryRepository.findById(modifyCommand.id());
        Optional<Category> optionalParentCategory = categoryRepository.findById(modifyCommand.parentId());
        if (optionalModifyingCategory.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND_BY_ID);
        }
        if(optionalParentCategory.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND_BY_ID, "존재하지 않는 부모 카테고리 입니다.");
        }
        Category modifyingCategory = optionalModifyingCategory.get();
        Category parent = optionalParentCategory.get();
        modifyingCategory.updateDetails(
                modifyCommand.name(),
                modifyCommand.description(),
                parent
        );
    }
}
