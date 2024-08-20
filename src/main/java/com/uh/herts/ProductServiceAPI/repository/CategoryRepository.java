package com.uh.herts.ProductServiceAPI.repository;

import com.uh.herts.ProductServiceAPI.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Cate findByUsername(String username);
//    List<Category> fi

    List<Category> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTagsContainingIgnoreCase(String name, String description, String tags);
    Page<Category> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTagsContainingIgnoreCase(String name, String description, String tags, Pageable pageable);

}