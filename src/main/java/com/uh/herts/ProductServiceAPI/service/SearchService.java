package com.uh.herts.ProductServiceAPI.service;

import com.uh.herts.ProductServiceAPI.dto.PagedResponse;
import com.uh.herts.ProductServiceAPI.dto.SearchResultDTO;

import java.util.List;

public interface SearchService {
    List<SearchResultDTO> search(String query);
    List<SearchResultDTO> searchOld(String query, int page, int size) ;
    PagedResponse<SearchResultDTO> searchs(String query, int page, int size);
    PagedResponse<SearchResultDTO> search(String query, int page, int size);
}
