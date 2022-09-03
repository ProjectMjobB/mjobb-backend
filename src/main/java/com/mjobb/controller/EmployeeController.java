package com.mjobb.controller;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.entity.Employee;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Language;
import com.mjobb.mapper.CustomModelMapper;
import com.mjobb.response.JobAdvertisementResponse;
import com.mjobb.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/employees/")
@RequiredArgsConstructor
@ApiOperation("Employees API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CustomModelMapper customModelMapper;

    @GetMapping("history/job-advertisements")
    @Secured({"ROLE_EMPLOYEE"})
    public ResponseEntity<List<JobAdvertisementResponse>> getAppliedJobAdvertisementsForCurrentUser() {
        return ResponseEntity.accepted().body(employeeService.getAppliedJobAdvertisementsForCurrentUser());
    }

    @PostMapping("add-language")
    @Secured({"ROLE_EMPLOYEE"})
    public ResponseEntity<Void> addLanguage(@RequestBody String language) {
        employeeService.addLanguage(language);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("{empId}/languages/")
    public ResponseEntity<List<Language>> getLanguagesByEmpId(@PathVariable Long empId) {
        return ResponseEntity.ok(employeeService.getLanguagesByEmpId(empId));
    }
    @PostMapping("/{empId}/many-languages")
    public ResponseEntity<Employee> addManyLanguage(@PathVariable Long empId, @RequestBody List<Integer> langIds) {
        return new ResponseEntity<>(employeeService.addManyLanguage(empId,langIds), HttpStatus.CREATED);
    }

    @GetMapping("remove-language")
    @Secured({"ROLE_EMPLOYEE"})
    public ResponseEntity<Void> removeLanguage(@RequestBody String language) {
        employeeService.removeLanguage(language);
        return ResponseEntity.accepted().build();
    }

}
