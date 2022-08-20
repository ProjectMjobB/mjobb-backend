package com.mjobb.controller;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.service.JobAdvertisementService;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;import com.mjobb.entity.Application;
import com.mjobb.entity.Employee;

@RestController
@RequestMapping("/api/v1.0/jobs/")
@RequiredArgsConstructor
@ApiOperation("Jobs API")
public class JobAdvertisementController {

    private final JobAdvertisementService jobAdvertisementService;


    @GetMapping("add/favorite")
    @Secured({"ROLE_EMPLOYEE"})
    public ResponseEntity<Void> addFavoriteJobForCurrentUser(@RequestParam @NotNull Long jobId) {
        jobAdvertisementService.addFavoriteJobForCurrentUser(jobId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("delete/favorite")
    @Secured({"ROLE_EMPLOYEE"})
    public ResponseEntity<Void> deleteFavoriteJobForCurrentUser(@RequestParam @NotNull Long jobId) {
        jobAdvertisementService.deleteFavoriteJobForCurrentUser(jobId);
        return ResponseEntity.accepted().build();
    }


    @PostMapping("update")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<JobAdvertisement> updateJob(@RequestBody JobAdvertisement jobAdvertisement) {
        return ResponseEntity.ok(jobAdvertisementService.save(jobAdvertisement));
    }

    @GetMapping("all")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<List<JobAdvertisement>> getCurrentCompanyJobs() {
        return ResponseEntity.ok(jobAdvertisementService.getMyCreatedJobs());
    }

    @GetMapping("opened")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<List<JobAdvertisement>> getCompanyApprovedJobs() {
        return ResponseEntity.ok(jobAdvertisementService.getMyOpenedJobs());
    }

    @PostMapping("create")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<JobAdvertisement> createJobAdvertisement(@RequestBody JobAdvertisementDto job) {
        return ResponseEntity.ok(jobAdvertisementService.createJobAdvertisement(job));
    }


    @PostMapping("apply")
    @Secured("ROLE_EMPLOYEE")
    public ResponseEntity<JobAdvertisement> applyJob(@RequestBody JobAdvertisement jobAdvertisement) {
        return ResponseEntity.ok(jobAdvertisementService.applyJobForUser(jobAdvertisement));
    }

    @GetMapping("/applied-users")
    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<List<Employee>> getApplications(@RequestParam Long id) {
        return ResponseEntity.ok(jobAdvertisementService.getEmployeesByAppliedJob(id));
    }


    @GetMapping("/delete")
    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Void> deleteJob(@RequestParam Long id) {
        jobAdvertisementService.deleteJobAdvertisement(id);
        return ResponseEntity.ok().build();
    }

}
