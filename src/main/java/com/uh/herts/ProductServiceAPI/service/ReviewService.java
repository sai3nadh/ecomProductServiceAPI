package com.uh.herts.ProductServiceAPI.service;

import com.uh.herts.ProductServiceAPI.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewsByProductId(Integer productId);
    ReviewDTO getReviewById(Integer reviewId);
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(Integer reviewId, ReviewDTO reviewDTO);
    void deleteReview(Integer reviewId);
}
