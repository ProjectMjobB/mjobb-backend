package com.mjobb.service.impl;

import com.mjobb.entity.*;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.*;
import com.mjobb.response.ApplicationResponse;
import com.mjobb.response.JobAdvertisementResponse;
import com.mjobb.service.CategoryService;
import com.mjobb.service.EmployeeService;
import com.mjobb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    private final EmployeeRepository employeeRepository;

    private final LanguageRepository languageRepository;

    private final CompanyRepository companyRepository;
    private final JobTypeRepository jobTypeRepository;
    private final CategoryService categoryService;
    @Override
    public List<JobAdvertisementResponse> getAppliedJobAdvertisementsForCurrentUser() {
        Employee user = (Employee) userService.getCurrentUser();
        List<ApplicationResponse> applicationResponses = new ArrayList<>();
        List<Application> applications = applicationRepository.findAllByEmployee(user);
        if (CollectionUtils.isEmpty(applications)) {
            return null;
        }
        List<JobAdvertisement> jobs = applications.stream().map(Application::getJobAdvertisement).toList();
        List<JobAdvertisementResponse> jobAdvertisementResponses = new ArrayList<>();
        for (JobAdvertisement jobAdvertisement:jobs) {
            JobType jobType = jobTypeRepository.findById(jobAdvertisement.getJobType().getId()).orElseThrow(() -> new WebServiceException("Not found JobType with id = " + jobAdvertisement.getJobType().getId()));

            JobAdvertisementResponse response = new JobAdvertisementResponse();
            response.setId(jobAdvertisement.getId());
            response.setTitle(jobAdvertisement.getTitle());
            response.setAddress(jobAdvertisement.getAddress());
            response.setYearsOfExperience(jobAdvertisement.getYearsOfExperience());
            response.setMinimumSalary(jobAdvertisement.getMinimumSalary());
            response.setMaximumSalary(jobAdvertisement.getMaximumSalary());
            response.setFile(jobAdvertisement.getFile());
            response.setDescription(jobAdvertisement.getDescription());
            response.setJobType(jobType.getName());
            response.setWorkingType(jobAdvertisement.getWorkingType());
            response.setCountry(jobAdvertisement.getCountry());
            response.setCity(jobAdvertisement.getCity());
            response.setApplications(jobAdvertisement.getApplications());
            response.setCompany(jobAdvertisement.getCompany());
            response.setComplaints(jobAdvertisement.getComplaints());
            response.setAccepted(response.isAccepted());
            response.setCreatedDate(jobAdvertisement.getCreatedDate());
            response.setUpdatedDate(jobAdvertisement.getUpdatedDate());
            response.setTags(jobAdvertisement.getTags());
            response.setLanguages(jobAdvertisement.getLanguages());
            Category category = categoryService.getCategoryById(jobAdvertisement.getCategory().getId());
            response.setCategory(category);
            jobAdvertisementResponses.add(response);

        }
        return jobAdvertisementResponses;
    }

    @Override
    public List<Long> getAppliedJobAdvertisementIdsForUser(long id) {
        Employee user = (Employee) userService.getUserById(id);
        List<Application> applications = applicationRepository.findAllByEmployee(user);
        if (CollectionUtils.isEmpty(applications)) {
            return null;
        }
        return applications.stream().map(Application::getJobAdvertisement).map(JobAdvertisement::getId).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public void addLanguage(String lang) {
        Employee employee = (Employee) userService.getCurrentUser();
        Language language = new Language();
        language.setName(lang);
        List<Language> languages = CollectionUtils.isEmpty(employee.getLanguages()) ? new ArrayList<>() : (List<Language>) employee.getLanguages();
        languages.add(language);
        userService.save(employee);
    }

    @Override
    public void removeLanguage(String language) {
        Employee employee = (Employee) userService.getCurrentUser();
        List<Language> languages = CollectionUtils.isEmpty(employee.getLanguages()) ? new ArrayList<>() : (List<Language>) employee.getLanguages();
        List<Language> newLanguages = languages.stream().filter(x -> x.getName().equalsIgnoreCase(language)).toList();
        employee.setLanguages((Set<Language>) newLanguages);
        userService.save(employee);
    }

    @Override
    public List<Language> getLanguagesByEmpId(Long empId) {

        Employee employee = employeeRepository.findById(empId).orElseThrow(()-> new WebServiceException("Employee not found"));
        return (List<Language>) employee.getLanguages();

    }

    @Override
    public Employee addManyLanguage(Long empId, List<Integer> langIds) {
        for (Integer languageId : langIds) {
            addLanguage(empId, languageRepository.findById(Long.valueOf(languageId)).orElseThrow(()-> new WebServiceException("Language not found")));
        }
        return employeeRepository.findById(empId).orElseThrow(() -> new WebServiceException("Not found Job with id = " + empId));

    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Long> getAppliedJobIdsAdvertisementIdsForUser(Long id) {
        Employee user = (Employee) userService.getUserById(id);
        List<Application> applications = applicationRepository.findAllByEmployee(user);
        if (CollectionUtils.isEmpty(applications)) {
           return null;
        }
        return applications.stream().map(Application::getJobAdvertisement).map(JobAdvertisement::getId).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public Language addLanguage(Long empId, Language addLanguageRequest) {
        Language language = employeeRepository.findById(empId).map(job -> {
            long langId = addLanguageRequest.getId();

            // tag is existed
            if (langId != 0L) {
                Language _lang = languageRepository.findById(langId)
                        .orElseThrow(() -> new WebServiceException("Not found Language with id = " + langId));
                job.addLanguage(_lang);
                employeeRepository.save(job);
                return _lang;
            }

            // add and create new Tag
            job.addLanguage(addLanguageRequest);
            return languageRepository.save(addLanguageRequest);
        }).orElseThrow(() -> new WebServiceException("Not found Tutorial with id = " + empId));
        return language;
    }
}
