package com.mjobb.service;

import com.mjobb.entity.JobAdvertisement;

import java.util.List;

public interface JobAdvertisementService {

    void addFavoriteJobForCurrentUser(Long jobId);

    void deleteFavoriteJobForCurrentUser(Long jobId);
    
    JobAdvertisement getJobAdvertisementById(Long jobId);

    void save(JobAdvertisement jobAdvertisement);

    List<JobAdvertisement> pendingApprovalJobs();

    void approveJobs(List<JobAdvertisement> jobs);

    void rejectJobs(List<JobAdvertisement> jobs);

}
