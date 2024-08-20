package com.uh.herts.ProductServiceAPI.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultDTO {
//    private Integer id;
//    private String type;  // "product" or "category"
//    private String name;
//    private String description;

    private Integer id;
    private String type;  // "product" or "category"
    private String name;
    private String description;
    private List<ProductDTO> products;  // List of products under a category

}
