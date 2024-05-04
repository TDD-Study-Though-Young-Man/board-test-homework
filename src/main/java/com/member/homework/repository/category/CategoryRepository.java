package com.member.homework.repository.category;

import com.member.homework.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    @Query("select max(c.id) from Category c")
    Long findMaxCategoryId();
}
