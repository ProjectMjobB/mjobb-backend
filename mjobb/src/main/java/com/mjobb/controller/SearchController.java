package com.mjobb.controller;

import com.mjobb.entity.JobAdvertisement;
import com.mjobb.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/search/")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public Page<JobAdvertisement> search(@RequestParam int page, @RequestParam int size, @RequestParam String query) {
        PageRequest pr = PageRequest.of(page, size);
        return searchService.searchJobs(query, pr);
    }

    @GetMapping("/actives")
    public Page<JobAdvertisement> actives(@RequestParam int page, @RequestParam int size) {
        PageRequest pr = PageRequest.of(page, size);
        return searchService.activeJobs(pr);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public Page<JobAdvertisement> all(@RequestParam int page, @RequestParam int size) {
        PageRequest pr = PageRequest.of(page, size);
        return searchService.allJobs(pr);
    }
}
