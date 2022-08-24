package com.mjobb.service.impl;

import com.mjobb.entity.Application;
import com.mjobb.entity.Employee;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Language;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.ApplicationRepository;
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

    @Override
    public List<JobAdvertisement> getAppliedJobAdvertisementsForCurrentUser() {
        Employee user = (Employee) userService.getCurrentUser();
        List<Application> applications = applicationRepository.findAllByEmployee(user);
        if (CollectionUtils.isEmpty(applications)) {
            throw new WebServiceException("The user has no job applications.");
        }
        return applications.stream().map(Application::getJobAdvertisement).toList();
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
}
