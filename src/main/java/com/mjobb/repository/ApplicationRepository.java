package com.mjobb.repository;

import com.mjobb.entity.Application;
import com.mjobb.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByEmployee(Employee employee);

}
