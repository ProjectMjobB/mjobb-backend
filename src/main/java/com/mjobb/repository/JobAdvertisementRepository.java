package com.mjobb.repository;

import com.mjobb.entity.JobAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Long> {

    List<JobAdvertisement> findAllByAccepted(boolean accepted);
    List<JobAdvertisement> findJobAdvertisementByTagsId(Long tagId);

    List<JobAdvertisement> findByTitleContaining(String title);
}
