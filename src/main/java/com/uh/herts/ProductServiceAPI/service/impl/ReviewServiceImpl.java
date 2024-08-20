package com.uh.herts.ProductServiceAPI.service.impl;


import com.uh.herts.ProductServiceAPI.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uh.herts.ProductServiceAPI.dto.ReviewDTO;
import com.uh.herts.ProductServiceAPI.entity.Product;
import com.uh.herts.ProductServiceAPI.entity.Review;
import com.uh.herts.ProductServiceAPI.exception.ResourceNotFoundException;
import com.uh.herts.ProductServiceAPI.mapper.ReviewMapper;
import com.uh.herts.ProductServiceAPI.repository.ProductRepository;
import com.uh.herts.ProductServiceAPI.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<ReviewDTO> getReviewsByProductId(Integer productId) {
        List<Review> reviews = reviewRepository.findByProduct_ProductId(productId);
        return reviews.stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found for this id :: " + reviewId));
        return reviewMapper.toDTO(review);
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = reviewMapper.toEntity(reviewDTO);

        // Set the product relationship
        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + reviewDTO.getProductId()));
        review.setProduct(product);

        // Set the creation timestamp
        review.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(savedReview);
    }

    @Override
    public ReviewDTO updateReview(Integer reviewId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found for this id :: " + reviewId));

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        // Assuming you don't update product or createdAt on update.

        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(updatedReview);
    }

    @Override
    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found for this id :: " + reviewId));
        reviewRepository.delete(review);
    }
}