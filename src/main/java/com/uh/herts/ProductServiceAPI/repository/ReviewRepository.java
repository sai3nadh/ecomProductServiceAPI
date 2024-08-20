package com.uh.herts.ProductServiceAPI.repository;


import com.uh.herts.ProductServiceAPI.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Find all reviews for a specific product
    List<Review> findByProduct_ProductId(Integer productId);

    // You can add more custom query methods here if needed
}