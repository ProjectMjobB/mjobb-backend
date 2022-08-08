package com.mjobb.service;

import com.mjobb.entity.Application;
import com.mjobb.entity.Employee;

import java.util.List;

public interface ApplicationService {

    List<Application> findAllApplicationForEmployee(Employee employee);

}
