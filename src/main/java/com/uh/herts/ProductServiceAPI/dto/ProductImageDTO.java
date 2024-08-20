package com.uh.herts.ProductServiceAPI.dto;

import lombok.Data;

@Data
public class ProductImageDTO {

    private Integer imageId;
    private String imageUrl;
    private String altText;

}
