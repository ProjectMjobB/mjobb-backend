package com.mjobb.service.impl;

import com.mjobb.entity.Application;
import com.mjobb.entity.Employee;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.ApplicationRepository;
import com.mjobb.service.EmployeeService;
import com.mjobb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
}
