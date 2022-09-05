package com.mjobb.controller;

import com.mjobb.entity.CompanyHistory;
import com.mjobb.entity.KeySkill;
import com.mjobb.service.CompanyHistoryService;
import com.mjobb.service.KeySkillService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/company-history")
@RequiredArgsConstructor
@ApiOperation("Company History API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyHistoryController {
    @Autowired
    CompanyHistoryService companyHistoryService;

    @PostMapping("/{resumeId}/create")
    public ResponseEntity<CompanyHistory> createCompanyHistory(@PathVariable(value = "resumeId") Long resumeId,
                                                   @RequestBody CompanyHistory companyHistoryRequest) {
        return new ResponseEntity<>(companyHistoryService.createCompanyHistory(resumeId,companyHistoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyHistory> updateCompanyHistory(@PathVariable("id") long id, @RequestBody CompanyHistory companyHistoryRequest) {

        return new ResponseEntity<>(companyHistoryService.updateCompanyHistory(id,companyHistoryRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCompanyHistory(@PathVariable("id") long id) {
        companyHistoryService.deleteCompanyHistory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/resume/{resumeId}/company-history")
    public ResponseEntity<List<CompanyHistory>> getAllCompanyHistoriesByResumeId(@PathVariable(value = "resumeId") Long resumeId) {

        return new ResponseEntity<>(companyHistoryService.getAllCompanyHistoriesByResumeId(resumeId), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CompanyHistory> getCompanyHistoryById(@PathVariable("id") long id) {
        return new ResponseEntity<>(companyHistoryService.getCompanyHistoryById(id), HttpStatus.OK);
    }
}
