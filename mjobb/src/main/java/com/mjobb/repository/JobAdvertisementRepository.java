package com.mjobb.repository;

import com.mjobb.entity.JobAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Long> {
}
