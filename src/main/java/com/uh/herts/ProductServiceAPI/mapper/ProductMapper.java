package com.uh.herts.ProductServiceAPI.mapper;


import com.uh.herts.ProductServiceAPI.dto.ProductDTO;
import com.uh.herts.ProductServiceAPI.dto.ProductImageDTO;
import com.uh.herts.ProductServiceAPI.dto.ReviewResDTO;
import com.uh.herts.ProductServiceAPI.entity.Category;
import com.uh.herts.ProductServiceAPI.entity.Product;
import com.uh.herts.ProductServiceAPI.entity.Review;
import com.uh.herts.ProductServiceAPI.entity.ProductImage;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;



@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);

        // Additional mapping if needed (e.g., reviews, images)
        dto.setReviews(product.getReviews() != null
                ? product.getReviews().stream().map(this::toReviewDTO).collect(Collectors.toList())
                : new ArrayList<>());

        dto.setImages(product.getImages() != null
                ? product.getImages().stream().map(this::toProductImageDTO).collect(Collectors.toList())
                : new ArrayList<>());

        return dto;
    }


    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            product.setCategory(category);
        }


        return product;
    }

    private ReviewResDTO toReviewDTO(Review review) {
        ReviewResDTO dto = new ReviewResDTO();
        dto.setReviewId(review.getReviewId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt().toLocalDateTime());
        return dto;
    }

    private ProductImageDTO toProductImageDTO(ProductImage image) {
        ProductImageDTO dto = new ProductImageDTO();
        dto.setImageId(image.getImageId());
        dto.setImageUrl(image.getImageUrl());
        dto.setAltText(image.getAltText());
        return dto;
    }


    private Review toReviewEntity(ReviewResDTO dto) {
        Review review = new Review();
        review.setReviewId(dto.getReviewId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCreatedAt(Timestamp.valueOf(dto.getCreatedAt()));
        return review;
    }

    private ProductImage toProductImageEntity(ProductImageDTO dto) {
        ProductImage image = new ProductImage();
        image.setImageId(dto.getImageId());
        image.setImageUrl(dto.getImageUrl());
        image.setAltText(dto.getAltText());
        return image;
    }


}
