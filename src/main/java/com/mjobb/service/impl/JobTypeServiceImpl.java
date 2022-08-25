package com.mjobb.service.impl;

import com.mjobb.entity.JobType;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.JobTypeRepository;
import com.mjobb.service.JobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobTypeServiceImpl implements JobTypeService {

    private final JobTypeRepository jobTypeRepository;

    @Override
    public List<JobType> getAllJobTypes(Optional<String> title) {
        List<JobType> jobTypes = new ArrayList<>();
        if (title.isEmpty())
            jobTypeRepository.findAll().forEach(jobTypes::add);
        else
            jobTypeRepository.findByNameContaining(String.valueOf(title)).forEach(jobTypes::add);
        if (jobTypes.isEmpty()) {
            return null;
        }
        return jobTypes;
    }

    @Override
    public JobType getJobTypeById(@PathVariable("id") long id) {
        JobType jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("Not found Job Type with id = " + id));
        return jobType;
    }

    @Override
    public JobType updateJobType(long id, JobType jobType) {
        JobType _jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("Not found Job Type with id = " + id));
        _jobType.setName(jobType.getName());
        return jobTypeRepository.save(_jobType);
    }



    @Override
    public JobType createJobType(JobType jobType) {
        JobType _jobType = jobTypeRepository.save(new JobType(jobType.getName()));
        return _jobType;
    }

    @Override
    public void deleteTJobType(@PathVariable("id") long id) {
        jobTypeRepository.deleteById(id);
    }
}
