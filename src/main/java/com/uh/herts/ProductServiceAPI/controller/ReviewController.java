package com.uh.herts.ProductServiceAPI.controller;

import com.uh.herts.ProductServiceAPI.dto.ReviewDTO;
import com.uh.herts.ProductServiceAPI.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable Integer productId) {
        log.info("GET /api/reviews/product/{} - Fetching reviews for product ID", productId);
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok().body(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Integer reviewId) {
        log.info("GET /api/reviews/{} - Fetching review by ID", reviewId);
        ReviewDTO reviewDTO = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok().body(reviewDTO);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        log.info("POST /api/reviews - Creating a new review");
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.status(201).body(createdReview);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Integer reviewId, @RequestBody ReviewDTO reviewDTO) {
        log.info("PUT /api/reviews/{} - Updating review", reviewId);
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok().body(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewId) {
        log.info("DELETE /api/reviews/{} - Deleting review", reviewId);
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
