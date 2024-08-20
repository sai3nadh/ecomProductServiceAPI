package com.uh.herts.ProductServiceAPI.controller;

import com.uh.herts.ProductServiceAPI.entity.Category;
import com.uh.herts.ProductServiceAPI.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200") // Angular's default port
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
//
    // Get all categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
//
//    // Get a single category by ID
//    @GetMapping("/{categoryId}")
//    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
//        Category category = categoryService.getCategoryById(categoryId);
//        if (category == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(category);
//    }
//
//    // Create a new category
//    @PostMapping
//    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
//        Category newCategory = categoryService.createCategory(category);
//        return ResponseEntity.ok(newCategory);
//    }
//
//    // Update an existing category
//    @PutMapping("/{categoryId}")
//    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category categoryDetails) {
//        Category updatedCategory = categoryService.updateCategory(categoryId, categoryDetails);
//        if (updatedCategory == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(updatedCategory);
//    }
//
//    // Delete a category
//    @DeleteMapping("/{categoryId}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
//        boolean isDeleted = categoryService.deleteCategory(categoryId);
//        if (!isDeleted) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.noContent().build();
//    }
}
