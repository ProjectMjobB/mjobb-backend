package com.mjobb.service.impl;

import com.mjobb.entity.*;
import com.mjobb.exception.UserNotFoundException;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.*;
import com.mjobb.request.AddTagRequest;
import com.mjobb.service.JobAdvertisementService;
import com.mjobb.service.UserService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobAdvertisementServiceImpl implements JobAdvertisementService {

    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final UserService userService;
    private final CompanyRepository companyRepository;
    private final ApplicationRepository applicationRepository;

    private final CategoryRepository categoryRepository;

    private final TagRepository tagRepository;

    private final JobTypeRepository jobTypeRepository;

    private final LanguageRepository languageRepository;

    public JobAdvertisement getJobAdvertisementById(long id) {
        JobAdvertisement jobAdvertisement = jobAdvertisementRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("Not found Tutorial with id = " + id));
        return jobAdvertisement;
    }

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
    public Language addLanguage(Long jobId, Language addLanguageRequest) {
        Language language = jobAdvertisementRepository.findById(jobId).map(job -> {
            long langId = addLanguageRequest.getId();

            // tag is existed
            if (langId != 0L) {
                Language _lang = languageRepository.findById(langId)
                        .orElseThrow(() -> new WebServiceException("Not found Language with id = " + langId));
                job.addLanguage(_lang);
                jobAdvertisementRepository.save(job);
                return _lang;
            }

            // add and create new Tag
            job.addLanguage(addLanguageRequest);
            return languageRepository.save(addLanguageRequest);
        }).orElseThrow(() -> new WebServiceException("Not found Tutorial with id = " + jobId));
        return language;
    }

    @Override
    public JobAdvertisement save(@NotNull JobAdvertisement jobAdvertisement) {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();

        jobAdvertisement.setCreatedDate(date);
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
    public List<JobAdvertisement> getAllJobs() {
        return jobAdvertisementRepository.findAll();
    }

    @Override
    public List<JobAdvertisement> getOpenedJobs() {
        return jobAdvertisementRepository.findAllByAccepted(true);
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
    public JobAdvertisement createJobAdvertisement(Long categoryId,Long jobTypeId, JobAdvertisement request) {
        User user = userService.getCurrentUser();
        Company company = companyRepository.findById(user.getId()).orElseThrow(() -> {
            throw new UserNotFoundException("Company not found");
        });
        JobAdvertisement jobAdvertisement = categoryRepository.findById(categoryId).map(category -> {
            request.setAccepted(false);
            request.setCategory(category);
            request.setCompany(company);
            request.setJobType(jobTypeRepository.findById(jobTypeId).orElseThrow(() -> {
                throw new WebServiceException("Job type not found");
            }));
            return jobAdvertisementRepository.save(request);
        }).orElseThrow(() -> new WebServiceException("Not found Category with id = " + categoryId));

        return jobAdvertisement;
    }

    @Override
    public List<JobAdvertisement> getAllJobsByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new WebServiceException("Not found Category with id = " + categoryId);
        }
        List<JobAdvertisement> jobs = jobAdvertisementRepository.findByCategoryId(categoryId);

        return jobs;
    }

    @Override
    public JobAdvertisement getJobByCategoryId(Long id) {
        JobAdvertisement job = jobAdvertisementRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("Not found Job with id = " + id));
        return job;
    }

    @Override
    public Tag addTagToJobAdvertisement(Long jobId, Tag tagRequest) {
        Tag tag = jobAdvertisementRepository.findById(jobId).map(job -> {
            long tagId = tagRequest.getId();

            // tag is existed
            if (tagId != 0) {
                Tag _tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new WebServiceException("Not found Tag with id = " + tagId));
                job.addTag(_tag);
                jobAdvertisementRepository.save(job);
                return _tag;
            }

            // add and create new Tag
            job.addTag(tagRequest);
            return tagRepository.save(tagRequest);
        }).orElseThrow(() -> new WebServiceException("Not found Job with id = " + jobId));
        return tag;
    }

    @Override
    public void removeTagToJobAdvertisement(AddTagRequest request) {
        Tag tag = tagRepository.findById(request.getTagId()).orElseThrow();
        JobAdvertisement jobAdvertisement = getJobAdvertisementById(request.getJobId());
        List<Tag> tags = CollectionUtils.isEmpty(jobAdvertisement.getTags()) ? new ArrayList<>() : (List<Tag>) jobAdvertisement.getTags();
        List<Tag> newTags = tags.stream().filter(x -> !x.getId().equals(tag.getId())).toList();
        jobAdvertisement.setTags((Set<Tag>) newTags);
        save(jobAdvertisement);
    }

    @Override
    public Tag addTag(Long jobId, Tag tagRequest) {
        Tag tag = jobAdvertisementRepository.findById(jobId).map(job -> {
            long tagId = tagRequest.getId();

            // tag is existed
            if (tagId != 0L) {
                Tag _tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new WebServiceException("Not found Tag with id = " + tagId));
                job.addTag(_tag);
                jobAdvertisementRepository.save(job);
                return _tag;
            }

            // add and create new Tag
            job.addTag(tagRequest);
            return tagRepository.save(tagRequest);
        }).orElseThrow(() -> new WebServiceException("Not found Tutorial with id = " + jobId));
        return tag;
    }

    @Override
    public void deleteTagFromJob( Long jobId, Long tagId) {
        JobAdvertisement jobAdvertisement = jobAdvertisementRepository.findById(jobId)
                .orElseThrow(() -> new WebServiceException("Not found Job with id = " + jobId));

        jobAdvertisement.removeTag(tagId);
        jobAdvertisementRepository.save(jobAdvertisement);
    }

    @Override
    public void saveAndFlush(JobAdvertisement jobAdvertisement) {
        jobAdvertisementRepository.saveAndFlush(jobAdvertisement);
    }

    @Override
    public JobAdvertisement addManyTag(Long jobId, List<Integer> tagIds) {
        for (Integer tagId : tagIds) {
            addTag(jobId, tagRepository.findById(Long.valueOf(tagId)).orElseThrow(() -> new WebServiceException("Not found Tag with id = " + tagId)));
        }
       return jobAdvertisementRepository.findById(jobId).orElseThrow(() -> new WebServiceException("Not found Job with id = " + jobId));
    }

    @Override
    public JobAdvertisement addManyLanguage(Long jobId, List<Integer> languageIds) {
        for (Integer languageId : languageIds) {
            addLanguage(jobId, languageRepository.findById(Long.valueOf(languageId)).orElseThrow(() -> new WebServiceException("Not found Language with id = " + languageId)));
        }
        return jobAdvertisementRepository.findById(jobId).orElseThrow(() -> new WebServiceException("Not found Job with id = " + jobId));
    }


    @Override
    public JobAdvertisement updateJobAdvertisement(long id, @RequestBody JobAdvertisement jobAdvertisement) {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();


        JobAdvertisement _job = jobAdvertisementRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("Not found Job with id = " + id));
        _job.setTitle(jobAdvertisement.getTitle());
        _job.setAccepted(true);
        _job.setAddress(jobAdvertisement.getAddress());
        _job.setCity(jobAdvertisement.getCity());
        _job.setCountry(jobAdvertisement.getCountry());
        _job.setCreatedDate(jobAdvertisement.getCreatedDate());
        _job.setUpdatedDate(date);
        _job.setFile(jobAdvertisement.getFile());
        _job.setMaximumSalary(jobAdvertisement.getMaximumSalary());
        _job.setMinimumSalary(jobAdvertisement.getMinimumSalary());
        _job.setTitle(jobAdvertisement.getTitle());
        _job.setWorkingType(jobAdvertisement.getWorkingType());
        _job.setYearsOfExperience(jobAdvertisement.getYearsOfExperience());

        return jobAdvertisementRepository.save(_job);

    }

    @Override
    public void deleteLanguageFromJob( Long jobId, Long langId) {
        JobAdvertisement jobAdvertisement = jobAdvertisementRepository.findById(jobId)
                .orElseThrow(() -> new WebServiceException("Not found Job with id = " + jobId));

        jobAdvertisement.removeLanguage(langId);
        jobAdvertisementRepository.save(jobAdvertisement);
    }


    @Override
    public JobAdvertisement applyJobForUser(long job_id) {
        Employee employee = (Employee) userService.getCurrentUser();
        JobAdvertisement job = jobAdvertisementRepository.findById(job_id).orElseThrow(() -> {
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
    public List<Employee> getEmployeesByAppliedJob(long jobId) {

        JobAdvertisement job = getJobAdvertisementById(jobId);
        List<Application> applications = job.getApplications();
        List<Employee> employees = new ArrayList<>();
        if (CollectionUtils.isEmpty(applications)) {
          return employees;
        }

        return applications.stream().map(Application::getEmployee).toList();
    }

    @Override
    public void deleteJobAdvertisement(Long jobId) {
        JobAdvertisement jobAdvertisement = getJobAdvertisementById(jobId);
        jobAdvertisementRepository.delete(jobAdvertisement);
    }

}
