package com.uh.herts.ProductServiceAPI.controller;

import com.uh.herts.ProductServiceAPI.dto.PagedResponse;
import com.uh.herts.ProductServiceAPI.dto.SearchResultDTO;
import com.uh.herts.ProductServiceAPI.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:4200") // Angular's default port
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<SearchResultDTO>> search(@RequestParam String query) {
        log.info("GET /api/search - Searching with query: {}", query);
        String serverInfo = "Handled by server: " + System.getenv("HOSTNAME");
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Info", serverInfo);

        List<SearchResultDTO> results = searchService.search(query);
        return ResponseEntity.ok().body(results);
    }


    @GetMapping("/api/search")
    public ResponseEntity<List<SearchResultDTO>> searchs(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("GET /api/search/api/search - Searching with query: {}, page: {}, size: {}", query, page, size);
        String serverInfo = "Handled by server: " + System.getenv("HOSTNAME");
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Info", serverInfo);
        List<SearchResultDTO> results = searchService.searchOld(query, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/api/searchP")
    public ResponseEntity<PagedResponse<SearchResultDTO>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("GET /api/search/api/searchP - Searching with query: {}, page: {}, size: {}", query, page, size);
        String serverInfo = "Handled by server: " + System.getenv("HOSTNAME");
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Info", serverInfo);
        PagedResponse<SearchResultDTO> results = searchService.search(query, page, size);
        return ResponseEntity.ok(results);
    }

}
