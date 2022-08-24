package com.mjobb.service;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.entity.JobAdvertisement;

import java.util.List;
import java.util.Optional;

import com.mjobb.entity.Employee;
import com.mjobb.entity.Tag;
import com.mjobb.request.AddTagRequest;

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
    List<Employee> getEmployeesByAppliedJob(Long jobId);
     List<JobAdvertisement> getAllJobs(Optional<String> title);
     List<JobAdvertisement> getOpenedJobs(Optional<String> title);

    void deleteJobAdvertisement(Long jobId);

    Tag addTagToJobAdvertisement(Long jobId, Tag tagRequest);

    void removeTagToJobAdvertisement(AddTagRequest request);


}
