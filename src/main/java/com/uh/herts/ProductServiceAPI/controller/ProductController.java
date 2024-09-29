package com.uh.herts.ProductServiceAPI.controller;

import com.uh.herts.ProductServiceAPI.dto.ProductDTO;
import com.uh.herts.ProductServiceAPI.entity.Product;
import com.uh.herts.ProductServiceAPI.exception.ResourceNotFoundException;
import com.uh.herts.ProductServiceAPI.mapper.ProductMapper;
import com.uh.herts.ProductServiceAPI.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        log.info("GET /api/products - Fetching all products");
        String serverInfo = "Handled by server: " + System.getenv("HOSTNAME");
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Info", serverInfo);
        Product product = productService.getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
        return ResponseEntity.ok().body(product);
    }


    @GetMapping("/batch")
    public ResponseEntity<List<ProductDTO>> getProductsByIds(@RequestParam List<Integer> ids) {
        log.info("GET /api/products/batch - Fetching products by IDs: {}", ids);
        String serverInfo = "Handled by server: " + System.getenv("HOSTNAME");
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Info", serverInfo);
        List<Product> products = productService.getProductsByIds(ids);
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(productDTOs);
    }
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        log.info("POST /api/products - Creating a new product");
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        log.info("PUT /api/products/{} - Updating product", id);
        String serverInfo = "Handled by server: " + System.getenv("HOSTNAME");
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Info", serverInfo);
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        log.info("DELETE /api/products/{} - Deleting product", id);
        String serverInfo = "Handled by server: " + System.getenv("HOSTNAME");
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Info", serverInfo);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
