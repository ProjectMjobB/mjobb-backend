package com.mjobb.controller;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Tag;
import com.mjobb.request.AddTagRequest;
import com.mjobb.service.JobAdvertisementService;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.mjobb.entity.Application;
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

    @GetMapping("current-company-all-jobs")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<List<JobAdvertisement>> getCurrentCompanyJobs() {
        return ResponseEntity.ok(jobAdvertisementService.getMyCreatedJobs());
    }
    @GetMapping("all-jobs")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<List<JobAdvertisement>> getAllJobs() {
       List<JobAdvertisement> jobs = jobAdvertisementService.getAllJobs();
        if (jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("all-opened-jobs")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<List<JobAdvertisement>> getOpenedJobs() {
        List<JobAdvertisement> jobs = jobAdvertisementService.getOpenedJobs();
        if (jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }


    @GetMapping("current-company-opened-jobs")
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

    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    @PostMapping("/{jobAdvertisementId}/add-tag")
    public ResponseEntity<?> addTag(@PathVariable(value = "jobAdvertisementId") Long jobAdvertisementId, @RequestBody Tag tagRequest) {
        Tag tag = jobAdvertisementService.addTagToJobAdvertisement(jobAdvertisementId, tagRequest);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PostMapping("/remove-tag")
    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Void> remove(@RequestBody AddTagRequest request){
        jobAdvertisementService.removeTagToJobAdvertisement(request);
        return ResponseEntity.ok().build();
    }

}
