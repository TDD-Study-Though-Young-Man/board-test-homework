package com.tdd.board.category.repository;

import com.tdd.board.category.domain.Category;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    @Query("select max(c.id) from Category c")
    Long findMaxCategoryId();
}
