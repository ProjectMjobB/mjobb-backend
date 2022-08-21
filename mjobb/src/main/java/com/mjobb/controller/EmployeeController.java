package com.mjobb.controller;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.mapper.CustomModelMapper;
import com.mjobb.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/employees/")
@RequiredArgsConstructor
@ApiOperation("Employees API")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CustomModelMapper customModelMapper;

    @GetMapping("history/job-advertisements")
    @Secured({"ROLE_EMPLOYEE"})
    public ResponseEntity<List<JobAdvertisementDto>> getAppliedJobAdvertisementsForCurrentUser() {
        return ResponseEntity.accepted().body(customModelMapper.mapList(employeeService.getAppliedJobAdvertisementsForCurrentUser(), JobAdvertisementDto.class));
    }
    @GetMapping("add-language")
    @Secured({"ROLE_EMPLOYEE"})
    public ResponseEntity<Void> addLanguage(@RequestBody String language) {
        employeeService.addLanguage(language);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("remove-language")
    @Secured({"ROLE_EMPLOYEE"})
    public ResponseEntity<Void> removeLanguage(@RequestBody String language) {
        employeeService.removeLanguage(language);
        return ResponseEntity.accepted().build();
    }
}
