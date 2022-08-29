package com.mjobb.service;

import com.mjobb.entity.Employee;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Language;

import java.util.List;

public interface EmployeeService {

    List<JobAdvertisement> getAppliedJobAdvertisementsForCurrentUser();

    List<Long> getAppliedJobAdvertisementIdsForUser(long id);

    void addLanguage(String language);

    void removeLanguage(String language);

    List<Language> getLanguagesByEmpId(Long empId);

    Employee addManyLanguage(Long empId, List<Integer> langIds);

    List<Employee> getAllEmployees();

    List<Long> getAppliedJobIdsAdvertisementIdsForUser(Long id);
}
