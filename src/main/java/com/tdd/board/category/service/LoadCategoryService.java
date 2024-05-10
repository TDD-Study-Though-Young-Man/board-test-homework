package com.tdd.board.category.service;

import com.tdd.board.category.domain.Category;
import com.tdd.board.category.repository.CategoryRepository;
import com.tdd.board.repository.category.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoadCategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> loadAllCategories() {
        // 관리자 권한 체크 필요
        return categoryRepository.findAll();
    }
}
