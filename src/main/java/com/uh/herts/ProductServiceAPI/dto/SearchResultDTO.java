package com.uh.herts.ProductServiceAPI.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultDTO {


    private Integer id;
    private String type;
    private String name;
    private String description;
    private List<ProductDTO> products;

}
