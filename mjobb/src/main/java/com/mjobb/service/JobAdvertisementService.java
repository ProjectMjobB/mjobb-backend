package com.mjobb.service;

import com.mjobb.entity.JobAdvertisement;

public interface JobAdvertisementService {

    void addFavoriteJobForCurrentUser(Long jobId);

    void deleteFavoriteJobForCurrentUser(Long jobId);
    
    JobAdvertisement getJobAdvertisementById(Long jobId);

    void save(JobAdvertisement jobAdvertisement);
}
