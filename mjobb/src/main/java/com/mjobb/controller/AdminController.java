package com.mjobb.controller;

import com.mjobb.entity.Comment;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.service.CommentService;
import com.mjobb.service.JobAdvertisementService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/admin/")
@RequiredArgsConstructor
@ApiOperation("Admin API")
public class AdminController {

    private final CommentService commentService;
    private final JobAdvertisementService jobAdvertisementService;

    @GetMapping("comments/waiting")
    public ResponseEntity<List<Comment>> getWaitingComments() {
        return ResponseEntity.ok(commentService.pendingApprovalComments());
    }


    @PostMapping("comments/approve")
    public ResponseEntity<Void> approveComments(@RequestBody List<Comment> comments) {
        commentService.approveComments(comments);
        return ResponseEntity.ok().build();
    }

    @GetMapping("jobs/waiting")
    public ResponseEntity<List<JobAdvertisement>> getWaitingJobs() {
        return ResponseEntity.ok(jobAdvertisementService.pendingApprovalJobs());
    }


    @PostMapping("jobs/approve")
    public ResponseEntity<Void> approveJobs(@RequestBody List<JobAdvertisement> comments) {
        jobAdvertisementService.approveJobs(comments);
        return ResponseEntity.ok().build();
    }

    @PostMapping("jobs/reject")
    public ResponseEntity<Void> rejectJobs(@RequestBody List<JobAdvertisement> comments) {
        jobAdvertisementService.rejectJobs(comments);
        return ResponseEntity.ok().build();
    }
}
