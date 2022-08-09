package com.mjobb.service.impl;

import com.mjobb.entity.Company;
import com.mjobb.entity.Employee;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.User;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.JobAdvertisementRepository;
import com.mjobb.service.JobAdvertisementService;
import com.mjobb.service.UserService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            throw new WebServiceException("User has no favorite jobs");
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
    public JobAdvertisement save(@NotNull JobAdvertisement jobAdvertisement) {
        Date date = new Date();
        if (Objects.isNull(jobAdvertisement.getCreatedDate())) {
            jobAdvertisement.setCreatedDate(date);
        } else {
            Company company = jobAdvertisement.getCompany();
            User user = userService.getCurrentUser();
            if (!company.getId().equals(user.getId())) {
                throw new WebServiceException("You are not authorized to perform this action");
            }
        }

        jobAdvertisement.setUpdatedDate(date);
        return jobAdvertisementRepository.save(jobAdvertisement);
    }

    @Override
    public List<JobAdvertisement> pendingApprovalJobs() {
        return jobAdvertisementRepository.findAllByAccepted(false);
    }

    @Override
    public void approveJobs(List<JobAdvertisement> jobs) {
        if (CollectionUtils.isEmpty(jobs)) {
            return;
        }
        jobs.forEach(jobAdvertisement -> {
            jobAdvertisement.setAccepted(true);
            jobAdvertisementRepository.save(jobAdvertisement);
        });
    }

    @Override
    public void rejectJobs(List<JobAdvertisement> jobs) {
        if (CollectionUtils.isEmpty(jobs)) {
            return;
        }
        jobs.forEach(jobAdvertisement -> {
            jobAdvertisement.setAccepted(false);
            jobAdvertisementRepository.save(jobAdvertisement);
        });
    }

    @Override
    public List<JobAdvertisement> getMyCreatedJobs() {
        Company company = (Company) userService.getCurrentUser();
        return company.getJobs();
    }

    @Override
    public List<JobAdvertisement> getMyOpenedJobs() {
        List<JobAdvertisement> jobs = getMyCreatedJobs();
        if (CollectionUtils.isEmpty(jobs)) {
            throw new WebServiceException("Jobs not found");
        }
        return jobs.stream().filter(JobAdvertisement::isAccepted).collect(Collectors.toList());
    }


}
