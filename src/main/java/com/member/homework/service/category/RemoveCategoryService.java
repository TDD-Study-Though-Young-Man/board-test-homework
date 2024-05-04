package com.member.homework.service.category;


import com.member.homework.domain.*;
import com.member.homework.exception.*;
import com.member.homework.repository.category.*;
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
    }
}
