package com.mjobb.service;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.entity.JobAdvertisement;

import java.util.List;
import java.util.Optional;

import com.mjobb.entity.Employee;
import com.mjobb.entity.Language;
import com.mjobb.entity.Tag;
import com.mjobb.request.AddTagRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface JobAdvertisementService {

    void addFavoriteJobForCurrentUser(Long jobId);

    void deleteFavoriteJobForCurrentUser(Long jobId);

    JobAdvertisement getJobAdvertisementById(Long jobId);

    Language addLanguage(Long jobId, Language addLanguageRequest);

    JobAdvertisement save(JobAdvertisement jobAdvertisement);

    List<JobAdvertisement> pendingApprovalJobs();

    void approveJobs(List<JobAdvertisement> jobs);

    void rejectJobs(List<JobAdvertisement> jobs);

    List<JobAdvertisement> getMyCreatedJobs();

    List<JobAdvertisement> getMyOpenedJobs();

    JobAdvertisement createJobAdvertisement(Long categoryId,Long jobTypeId,JobAdvertisement jobAdvertisement);

    JobAdvertisement updateJobAdvertisement(long id, @RequestBody JobAdvertisement jobAdvertisement);

    void deleteLanguageFromJob(Long jobId, Long langId);

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
    void deleteTagFromJob(Long jobId, Long tagId);

    void saveAndFlush(JobAdvertisement jobAdvertisement);
}
