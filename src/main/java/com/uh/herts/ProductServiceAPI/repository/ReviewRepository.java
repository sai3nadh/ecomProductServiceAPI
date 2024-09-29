package com.uh.herts.ProductServiceAPI.repository;


import com.uh.herts.ProductServiceAPI.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByProduct_ProductId(Integer productId);
}