package com.mjobb.service.impl;

import com.mjobb.entity.Application;
import com.mjobb.entity.Employee;
import com.mjobb.repository.ApplicationRepository;
import com.mjobb.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public List<Application> findAllApplicationForEmployee(Employee employee) {
        return applicationRepository.findAllByEmployee(employee);
    }
}
