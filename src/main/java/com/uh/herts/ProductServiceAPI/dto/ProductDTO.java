package com.uh.herts.ProductServiceAPI.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {

    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Integer categoryId;
    private List<ReviewResDTO> reviews;
    private List<ProductImageDTO> images;

}