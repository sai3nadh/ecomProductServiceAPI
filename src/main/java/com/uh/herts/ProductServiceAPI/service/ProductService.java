package com.uh.herts.ProductServiceAPI.service;

import com.uh.herts.ProductServiceAPI.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Integer id);
    Product createProduct(Product product);
    Product updateProduct(Integer id, Product productDetails);
    void deleteProduct(Integer id);

    List<Product> getProductsByIds(List<Integer> ids);
}
