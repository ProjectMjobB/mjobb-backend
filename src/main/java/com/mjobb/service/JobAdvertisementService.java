package com.mjobb.service;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.entity.JobAdvertisement;

import java.util.List;

public interface JobAdvertisementService {

    void addFavoriteJobForCurrentUser(Long jobId);

    void deleteFavoriteJobForCurrentUser(Long jobId);

    JobAdvertisement getJobAdvertisementById(Long jobId);

    JobAdvertisement save(JobAdvertisement jobAdvertisement);

    List<JobAdvertisement> pendingApprovalJobs();

    void approveJobs(List<JobAdvertisement> jobs);

    void rejectJobs(List<JobAdvertisement> jobs);

    List<JobAdvertisement> getMyCreatedJobs();

    List<JobAdvertisement> getMyOpenedJobs();

    JobAdvertisement createJobAdvertisement(JobAdvertisementDto jobAdvertisement);

    JobAdvertisement applyJobForUser(JobAdvertisement jobAdvertisement);

}