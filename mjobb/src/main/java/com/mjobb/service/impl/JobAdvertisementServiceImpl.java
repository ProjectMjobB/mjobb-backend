package com.mjobb.service.impl;

import com.mjobb.entity.Employee;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.JobAdvertisementRepository;
import com.mjobb.service.JobAdvertisementService;
import com.mjobb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobAdvertisementServiceImpl implements JobAdvertisementService {

    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final UserService userService;


    @Override
    public void addFavoriteJobForCurrentUser(Long jobId) {
        Employee employee = (Employee) userService.getCurrentUser();
        List<JobAdvertisement> favoriteJobs = employee.getFavoriteJobs();
        if (CollectionUtils.isEmpty(favoriteJobs)) {
            favoriteJobs = new ArrayList<>();
        }

        JobAdvertisement jobAdvertisement = jobAdvertisementRepository.findById(jobId).orElseThrow(() -> new WebServiceException("Job not found this id: " + jobId));

        favoriteJobs.add(jobAdvertisement);
        employee.setFavoriteJobs(favoriteJobs);

        userService.save(employee);
    }

    @Override
    public void deleteFavoriteJobForCurrentUser(Long jobId) {
        Employee employee = (Employee) userService.getCurrentUser();
        List<JobAdvertisement> favoriteJobs = employee.getFavoriteJobs();
        if (CollectionUtils.isEmpty(favoriteJobs)) {
           throw  new WebServiceException("User has no favorite jobs");
        }

        JobAdvertisement jobAdvertisement = jobAdvertisementRepository.findById(jobId).orElseThrow(() -> new WebServiceException("Job not found this id: " + jobId));

        favoriteJobs.remove(jobAdvertisement);
        employee.setFavoriteJobs(favoriteJobs);

        userService.save(employee);
    }

    @Override
    public JobAdvertisement getJobAdvertisementById(Long jobId) {
        return jobAdvertisementRepository.findById(jobId).orElseThrow(() -> new WebServiceException("Job not found this id: " + jobId));
    }

    @Override
    public void save(JobAdvertisement jobAdvertisement) {
        jobAdvertisementRepository.save(jobAdvertisement);
    }

}
