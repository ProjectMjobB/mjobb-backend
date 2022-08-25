package com.mjobb.service;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.entity.JobAdvertisement;

import java.util.List;
import java.util.Optional;

import com.mjobb.entity.Employee;
import com.mjobb.entity.Tag;
import com.mjobb.request.AddTagRequest;
import org.springframework.web.bind.annotation.PathVariable;

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

    JobAdvertisement createJobAdvertisement(Long categoryId,Long jobTypeId,JobAdvertisement jobAdvertisement);

    JobAdvertisement applyJobForUser(long job_id);
    List<Employee> getEmployeesByAppliedJob(Long jobId);
     List<JobAdvertisement> getAllJobs();
     List<JobAdvertisement> getOpenedJobs();
     List<JobAdvertisement> getAllJobsByCategoryId(Long categoryId);

    JobAdvertisement getJobByCategoryId(Long id);

    void deleteJobAdvertisement(Long jobId);

    Tag addTagToJobAdvertisement(Long jobId, Tag tagRequest);
    Tag addTag(Long jobId, Tag tagRequest);
    void removeTagToJobAdvertisement(AddTagRequest request);
    JobAdvertisement getJobAdvertisementById(long id);
    void deleteTagFromTutorial(Long jobId, Long tagId);
}
