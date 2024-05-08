package com.tdd.board.category.dto;

public record CreateCategoryServiceRequest(String name, String description, Long parentId) {
}
