package com.uh.herts.ProductServiceAPI.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDTO {
    private Integer reviewId;
    private Integer rating;
    private String comment;
    private Timestamp createdAt;
    private Integer productId;


}
