package com.mjobb.controller;

import com.mjobb.entity.JobType;
import com.mjobb.service.JobTypeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/job-types/")
@RequiredArgsConstructor
@ApiOperation("Job Type API")
public class JobTypeController {
    private final JobTypeService jobTypeService;

    @GetMapping
    public ResponseEntity<List<JobType>> getAllJobTypes(@RequestParam(required = false) String title) {

        return new ResponseEntity<>(jobTypeService.getAllJobTypes(Optional.ofNullable(title)), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobType> getJobTypeById(@PathVariable("id") long id) {
        return new ResponseEntity<>(jobTypeService.getJobTypeById(id), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PostMapping
    public ResponseEntity<JobType> createJobType(@RequestBody JobType jobType) {

        return new ResponseEntity<>(jobTypeService.createJobType(jobType), HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PutMapping("/{id}")
    public ResponseEntity<JobType> updateJobType(@PathVariable("id") long id, @RequestBody JobType jobType) {

        return new ResponseEntity<>(jobTypeService.updateJobType(id,jobType), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteJobType(@PathVariable("id") long id) {
        jobTypeService.deleteTJobType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
