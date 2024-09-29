package com.uh.herts.ProductServiceAPI.service.impl;

import com.uh.herts.ProductServiceAPI.dto.PagedResponse;
import com.uh.herts.ProductServiceAPI.dto.ProductDTO;
import com.uh.herts.ProductServiceAPI.dto.SearchResultDTO;
import com.uh.herts.ProductServiceAPI.entity.Category;
import com.uh.herts.ProductServiceAPI.entity.Product;
import com.uh.herts.ProductServiceAPI.exception.ProductAPIException;
import com.uh.herts.ProductServiceAPI.mapper.ProductMapper;
import com.uh.herts.ProductServiceAPI.repository.CategoryRepository;
import com.uh.herts.ProductServiceAPI.repository.ProductRepository;
import com.uh.herts.ProductServiceAPI.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper ;//= new ProductMapper();
    @Autowired
    private CategoryRepository categoryRepository;
//    @Override

/*    public List<SearchResultDTO> searchww(String query) {
        List<SearchResultDTO> results = new ArrayList<>();

        // Search for products by name or description
        List<Product> products = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        results.addAll(products.stream()
                .map(this::mapProductToSearchResult)
                .collect(Collectors.toList()));

        // Search for categories by name, description, or tags
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTagsContainingIgnoreCase(query, query, query);
        results.addAll(categories.stream()
                .map(this::mapCategoryToSearchResultWithProducts)
                .collect(Collectors.toList()));

        return results;
    }*/

//    @Override
    public List<SearchResultDTO> searchPagee(String query, int page, int size) {
        List<SearchResultDTO> results = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);

        // Search for products by name or description
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, pageable);
        for (Product product : productPage) {
            results.add(mapCategoryWithSingleProductToSearchResult(product.getCategory(), product));
        }

        // Search for categories by name, description, or tags
        Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTagsContainingIgnoreCase(query, query, query, pageable);
        for (Category category : categoryPage) {
            List<Product> categoryProducts = productRepository.findByCategory(category);
            results.add(mapCategoryWithProductsToSearchResult(category, categoryProducts));
        }

        return results;
    }

    @Override
    public PagedResponse<SearchResultDTO> search(String query, int page, int size) {
        List<SearchResultDTO> results = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);
        long totalProducts = 0;  // Initialize total product count

        // Search for products by name or description with pagination
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, pageable);

        if (productPage.hasContent()) {
            totalProducts += productPage.getTotalElements();  // Increment total product count
            for (Product product : productPage.getContent()) {
                Category category = product.getCategory();

                SearchResultDTO dto = new SearchResultDTO();
                dto.setId(category.getId());
                dto.setType("category");
                dto.setName(category.getName());
                dto.setDescription(category.getDescription());

                List<ProductDTO> productDTOs = new ArrayList<>();
                productDTOs.add(productMapper.toDTO(product));

                dto.setProducts(productDTOs);
                results.add(dto);
            }
        } else {
            // Search for categories by name, description, or tags with pagination
            Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTagsContainingIgnoreCase(query, query, query, pageable);
//            totalProducts += categoryPage.getContent().stream().count();
//            totalProducts += categoryPage.getContent()..size();  // Add the number of products in this category
//            categoryProducts.size();  // Increment total product count
            for (Category category : categoryPage.getContent()) {
                totalProducts += category.getProducts().size();
                results.add(mapCategoryToSearchResultWithProducts(category));
            }
        }

        // If no results were found, throw a ProductAPIException
        if (results.isEmpty()) {
            throw new ProductAPIException(HttpStatus.NOT_FOUND, "No products or categories found for the query: " + query);
        }


        PagedResponse<SearchResultDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setTotalProducts(totalProducts);  // Set the total product count
        pagedResponse.setContent(results);
        pagedResponse.setPage(page);
        pagedResponse.setSize(size);
        pagedResponse.setTotalElements(productPage.getTotalElements());
        pagedResponse.setTotalPages(productPage.getTotalPages());

        return pagedResponse;
    }


    @Override
    public PagedResponse<SearchResultDTO> searchs(String query, int page, int size) {
        List<SearchResultDTO> results = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);

        // Search for products by name or description with pagination
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, pageable);

        if (productPage.hasContent()) {
            for (Product product : productPage.getContent()) {
                Category category = product.getCategory();

                SearchResultDTO dto = new SearchResultDTO();
                dto.setId(category.getId());
                dto.setType("category");
                dto.setName(category.getName());
                dto.setDescription(category.getDescription());

                List<ProductDTO> productDTOs = new ArrayList<>();
                productDTOs.add(productMapper.toDTO(product));

                dto.setProducts(productDTOs);
                results.add(dto);
            }
        } else {
            // Search for categories by name, description, or tags with pagination
            Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTagsContainingIgnoreCase(query, query, query, pageable);
            for (Category category : categoryPage.getContent()) {
                results.add(mapCategoryToSearchResultWithProducts(category));
            }
        }

        // If no results were found, throw a ProductAPIException
        if (results.isEmpty()) {
            throw new ProductAPIException(HttpStatus.NOT_FOUND, "No products or categories found for the query: " + query);
        }

        PagedResponse<SearchResultDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setContent(results);
        pagedResponse.setPage(page);
        pagedResponse.setSize(size);
        pagedResponse.setTotalElements(productPage.getTotalElements());
        pagedResponse.setTotalPages(productPage.getTotalPages());

        return pagedResponse;
    }

    @Override
    public List<SearchResultDTO> searchOld(String query, int page, int size) {
        List<SearchResultDTO> results = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);

        // Search for products by name or description with pagination
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, pageable);

        if (productPage.hasContent()) {
            for (Product product : productPage.getContent()) {
                Category category = product.getCategory();

                SearchResultDTO dto = new SearchResultDTO();
                dto.setId(category.getId());
                dto.setType("category");
                dto.setName(category.getName());
                dto.setDescription(category.getDescription());

                List<ProductDTO> productDTOs = new ArrayList<>();
                productDTOs.add(productMapper.toDTO(product));

                dto.setProducts(productDTOs);
                results.add(dto);
            }
        } else {
            // Search for categories by name, description, or tags with pagination
            Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTagsContainingIgnoreCase(query, query, query, pageable);
            results.addAll(categoryPage.getContent().stream()
                    .map(this::mapCategoryToSearchResultWithProducts)
                    .collect(Collectors.toList()));
        }

        // If no results were found, throw a ProductAPIException
        if (results.isEmpty()) {
            throw new ProductAPIException(HttpStatus.NOT_FOUND, "No products or categories found for the query: " + query);
        }

        return results;
    }
    private SearchResultDTO mapCategoryWithSingleProductToSearchResult(Category category, Product product) {
        SearchResultDTO dto = new SearchResultDTO();
        dto.setId(category.getId());
        dto.setType("category");
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        List<ProductDTO> productDTOs = new ArrayList<>();
        productDTOs.add(productMapper.toDTO(product));

        dto.setProducts(productDTOs);
        return dto;
    }
    private SearchResultDTO mapCategoryWithProductsToSearchResult(Category category, List<Product> products) {
        SearchResultDTO dto = new SearchResultDTO();
        dto.setId(category.getId());
        dto.setType("category");
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        dto.setProducts(productDTOs);
        return dto;
    }
    @Override
    public List<SearchResultDTO> search(String query) {
        List<SearchResultDTO> results = new ArrayList<>();

        // Search for products by name or description
        List<Product> products = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);

        if(products.size()>0) {

            for (Product product : products) {
                // Fetch the associated category
                Category category = product.getCategory();

                // Create the SearchResultDTO
                SearchResultDTO dto = new SearchResultDTO();
                dto.setId(category.getId());
                dto.setType("category");
                dto.setName(category.getName());
                dto.setDescription(category.getDescription());

                // Convert the product to ProductDTO and add it to the products list
                List<ProductDTO> productDTOs = new ArrayList<>();
                productDTOs.add(productMapper.toDTO(product));

                // Set the products under this category
                dto.setProducts(productDTOs);

                // Add the result to the list
                results.add(dto);
            }
        }
        else {

            // Search for categories by name, description, or tags
            List<Category> categories = categoryRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTagsContainingIgnoreCase(query, query, query);
            results.addAll(categories.stream()
                    .map(this::mapCategoryToSearchResultWithProducts)
                    .collect(Collectors.toList()));

        }
        // If no results were found, throw a ProductAPIException
        if (results.isEmpty()) {
            throw new ProductAPIException(HttpStatus.NOT_FOUND, "No products or categories found for the query: " + query);
        }
        return results;
    }

    private SearchResultDTO mapProductToSearchResult(Product product) {
        SearchResultDTO dto = new SearchResultDTO();
        dto.setId(product.getProductId());
        dto.setType("product");
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());

        return dto;
    }

    private SearchResultDTO mapCategoryToSearchResultWithProducts(Category category) {
        SearchResultDTO dto = new SearchResultDTO();
        dto.setId(category.getId());
        dto.setType("category");
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        // Get all products under this category
        List<Product> products = productRepository.findByCategory(category);
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        // Set products only for category results
        dto.setProducts(productDTOs);
        return dto;
    }

}
