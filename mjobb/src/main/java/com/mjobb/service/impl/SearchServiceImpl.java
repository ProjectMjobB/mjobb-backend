package com.mjobb.service.impl;


import com.mjobb.entity.JobAdvertisement;
import com.mjobb.repository.JobPageableRepository;
import com.mjobb.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final JobPageableRepository jobPageableRepository;

    @Override
    public Page<JobAdvertisement> searchJobs(String query, Pageable pageable) {
        return jobPageableRepository.findAllByTitleContainsIgnoreCaseOrAddressContainsIgnoreCaseOrTypeContainsIgnoreCaseAndAccepted(query, query, query, true, pageable);
    }

    @Override
    public Page<JobAdvertisement> activeJobs(Pageable pageable) {
        return jobPageableRepository.findAllByAccepted(true, pageable);
    }

    @Override
    public Page<JobAdvertisement> allJobs(Pageable pageable) {
        return jobPageableRepository.findAll(pageable);
    }


}
