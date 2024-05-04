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
        Optional<Category> optionalCategory = categoryRepository.findById(modifyCommand.id());
        if (optionalCategory.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND_BY_ID);
        }
        Category modifyingCategory = optionalCategory.get();
        Category parent = categoryRepository.findById(modifyCommand.parentId()).get();
        modifyingCategory.updateDetails(
                modifyCommand.name(),
                modifyCommand.description(),
                parent
        );
    }
}
