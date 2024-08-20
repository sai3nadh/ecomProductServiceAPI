package com.uh.herts.ProductServiceAPI.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResDTO {

    private Integer reviewId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

}
