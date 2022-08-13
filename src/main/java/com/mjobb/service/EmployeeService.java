package com.mjobb.service;

import com.mjobb.entity.JobAdvertisement;

import java.util.List;

public interface EmployeeService {

    List<JobAdvertisement> getAppliedJobAdvertisementsForCurrentUser();
}
