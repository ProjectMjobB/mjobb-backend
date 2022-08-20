package com.mjobb.service.impl;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.entity.*;
import com.mjobb.exception.UserNotFoundException;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.ApplicationRepository;
import com.mjobb.repository.CompanyRepository;
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
    private final CompanyRepository companyRepository;
    private final ApplicationRepository applicationRepository;


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
            User company = jobAdvertisement.getCompany();
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
            JobAdvertisement job = jobAdvertisementRepository.findById(jobAdvertisement.getId()).orElseThrow(() -> {
                throw new WebServiceException("Job can not update");
            });
            job.setAccepted(true);
            jobAdvertisementRepository.save(job);
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

    @Override
    public JobAdvertisement createJobAdvertisement(JobAdvertisementDto request) {
        User user = userService.getCurrentUser();
        Company company = companyRepository.findById(user.getId()).orElseThrow(() -> {
            throw new UserNotFoundException("Company not found");
        });
        JobAdvertisement jobAdvertisement = JobAdvertisement.builder()
                .title(request.getTitle())
                .yearsOfExperience(request.getYearsOfExperience())
                .minimumSalary(request.getMinimumSalary())
                .maximumSalary(request.getMaximumSalary())
                .file(request.getFile())
                .type(request.getType())
                .workingType(request.getWorkingType())
                .accepted(false)
                .build();
        jobAdvertisement.setCompany(company);
        return save(jobAdvertisement);
    }

    @Override
    public JobAdvertisement applyJobForUser(JobAdvertisement jobAdvertisement) {
        Employee employee = (Employee) userService.getCurrentUser();
        JobAdvertisement job = jobAdvertisementRepository.findById(jobAdvertisement.getId()).orElseThrow(() -> {
            throw new WebServiceException("Job not found");
        });

        Application application = Application.builder()
                .jobAdvertisement(job)
                .employee(employee)
                .build();


        applicationRepository.save(application);

        return job;
    }

    @Override
    public List<Employee> getEmployeesByAppliedJob(Long jobId) {

        JobAdvertisement job = getJobAdvertisementById(jobId);
        List<Application> applications = job.getApplications();
        if (CollectionUtils.isEmpty(applications)) {
            throw new WebServiceException("No applications for this job");
        }

        return applications.stream().map(Application::getEmployee).toList();
    }

    @Override
    public void deleteJobAdvertisement(Long jobId) {
        JobAdvertisement jobAdvertisement = getJobAdvertisementById(jobId);
        jobAdvertisementRepository.delete(jobAdvertisement);
    }

}
