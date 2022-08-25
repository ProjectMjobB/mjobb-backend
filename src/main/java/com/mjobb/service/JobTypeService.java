package com.mjobb.service;

import com.mjobb.entity.JobType;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface JobTypeService {
    List<JobType> getAllJobTypes(Optional<String> title);

    JobType getJobTypeById(@PathVariable("id") long id);

    JobType updateJobType(long id, JobType jobType);

    JobType createJobType(JobType jobType);

    void deleteTJobType(@PathVariable("id") long id);
}
