package com.uh.herts.ProductServiceAPI.controller;

import com.uh.herts.ProductServiceAPI.entity.Category;
import com.uh.herts.ProductServiceAPI.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200") // Angular's default port
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("GET /api/categories - Fetching all categories");
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @GetMapping("/testDocker")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {
        log.info("GET /api/categories/testDocker - Fetching all categories with server info");
        String serverInfo = request.getServerName() ;//"Handled by server: localhost:8083"; // Hardcode or use dynamic construction
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Info", serverInfo);
        List<Category> categories = categoryService.getAllCategories();
        log.info("Server info to be included in header: " + serverInfo);
        return ResponseEntity.ok().headers(headers).body(categories);

    }
}
