package com.member.homework.service.category.dto;

import jakarta.validation.constraints.*;

public record ModifyCategoryServiceRequest(
        @Min(value = 1) long id,
        @NotBlank @Size(min = 2, max = 50, message = "카테고리 이름은 2자 이상, 50자 이하여야 합니다.") String name,
                                           @Size(max = 200, message = "카테고리 설명은 200자를 초과할 수 없습니다.") String description,
                                           @Min(value = 1, message = "부모 ID 는 1 이상이어야 합니다.") long parentId) {
}
