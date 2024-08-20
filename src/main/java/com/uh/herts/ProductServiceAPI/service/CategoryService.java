package com.uh.herts.ProductServiceAPI.service;

import com.uh.herts.ProductServiceAPI.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category createCategory(Category category);

    Category updateCategory(UUID id, Category category);

    void deleteCategory(UUID id);

    Category getCategoryById(UUID id);

    List<Category> getAllCategories();
}
