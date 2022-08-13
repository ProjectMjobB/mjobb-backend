package com.mjobb.repository;

import com.mjobb.entity.JobAdvertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobPageableRepository extends PagingAndSortingRepository<JobAdvertisement, Long> {


    Page<JobAdvertisement> findAllByAccepted(boolean accepted, Pageable pageable);

    Page<JobAdvertisement> findAllByTitleContainsIgnoreCaseOrCategoryContainsIgnoreCaseOrAddressContainsIgnoreCaseOrTypeContainsIgnoreCaseAndAccepted(String title, String category, String address, String type, boolean accepted, Pageable pageable);
}
