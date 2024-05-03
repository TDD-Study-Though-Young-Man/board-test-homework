package com.member.homework.service.category;

import com.member.homework.domain.*;
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

    }
}
