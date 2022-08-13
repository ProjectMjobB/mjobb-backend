package com.mjobb.service;

import com.mjobb.entity.JobAdvertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    Page<JobAdvertisement> searchJobs(String query, Pageable pageable);

    Page<JobAdvertisement> activeJobs(Pageable pageable);

    Page<JobAdvertisement> allJobs(Pageable pageable);

}
