package com.mjobb.controller;

import com.mjobb.dto.JobAdvertisementDto;
import com.mjobb.entity.*;
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


    @PutMapping("/{id}")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<JobAdvertisement> updateJobAdvertisement(@PathVariable("id") long id, @RequestBody JobAdvertisement jobAdvertisement) {

        return new ResponseEntity<>(jobAdvertisementService.updateJobAdvertisement(id,jobAdvertisement), HttpStatus.OK);
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


    @GetMapping("/jobAdvertisements/{categoryId}/jobs")
    public ResponseEntity<List<JobAdvertisement>> getAllJobsByCategoryId(@PathVariable(value = "categoryId") Long categoryId) {
        return new ResponseEntity<>(jobAdvertisementService.getAllJobsByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping("category/{id}")
    public ResponseEntity<JobAdvertisement> getJobByCategoryId(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(jobAdvertisementService.getJobByCategoryId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobAdvertisement> getJobAdvertisementById(@PathVariable("id") long id) {
        return new ResponseEntity<>(jobAdvertisementService.getJobAdvertisementById(id), HttpStatus.OK);
    }

    @GetMapping("current-company-opened-jobs")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<List<JobAdvertisement>> getCompanyApprovedJobs() {
        return ResponseEntity.ok(jobAdvertisementService.getMyOpenedJobs());
    }

    @PostMapping("/{categoryId}/{jobTypeId}/jobAdvertisements")
    @Secured({"ROLE_COMPANY"})
    public ResponseEntity<JobAdvertisement> createJobAdvertisement(@PathVariable(value = "categoryId") Long categoryId,
                                                                   @PathVariable(value = "jobTypeId") Long jobTypeId,
                                                                   @RequestBody JobAdvertisement jobAdvertisementRequest) {
        return ResponseEntity.ok(jobAdvertisementService.createJobAdvertisement(categoryId,jobTypeId, jobAdvertisementRequest));
    }


    @PostMapping("apply/{job_id}")
    @Secured("ROLE_EMPLOYEE")
    public ResponseEntity<JobAdvertisement> applyJob(@PathVariable long job_id) {
        return ResponseEntity.ok(jobAdvertisementService.applyJobForUser(job_id));
    }

    @GetMapping("/applied-users")
    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<List<Employee>> getApplications(@RequestParam Long id) {
        return ResponseEntity.ok(jobAdvertisementService.getEmployeesByAppliedJob(id));
    }


    @DeleteMapping("/delete")
    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Void> deleteJob(@RequestParam Long id) {
        jobAdvertisementService.deleteJobAdvertisement(id);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    @PostMapping("/{jobId}/tags")
    public ResponseEntity<Tag> addTag(@PathVariable Long jobId, @RequestBody Tag addTagRequest) {
        return new ResponseEntity<>(jobAdvertisementService.addTag(jobId,addTagRequest), HttpStatus.CREATED);
    }

    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    @PostMapping("/{jobId}/languages")
    public ResponseEntity<Language> addLanguage(@PathVariable Long jobId, @RequestBody Language addLanguageRequest) {
        return new ResponseEntity<>(jobAdvertisementService.addLanguage(jobId,addLanguageRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{jobId}/languages/{langId}")
    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<HttpStatus> deleteLanguageFromJob(@PathVariable(value = "jobId") Long jobId, @PathVariable(value = "langId") Long langId) {
        jobAdvertisementService.deleteLanguageFromJob(jobId, langId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{jobId}/tags/{tagId}")
    @Secured({"ROLE_COMPANY","ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<HttpStatus> deleteTagFromJob(@PathVariable(value = "jobId") Long jobId, @PathVariable(value = "tagId") Long tagId) {
        jobAdvertisementService.deleteTagFromJob(jobId, tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
