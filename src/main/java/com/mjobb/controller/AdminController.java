package com.mjobb.controller;

import com.mjobb.entity.Comment;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.User;
import com.mjobb.service.CommentService;
import com.mjobb.service.JobAdvertisementService;
import com.mjobb.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/admin/")
@RequiredArgsConstructor
@ApiOperation("Admin API")
public class AdminController {

    private final CommentService commentService;
    private final JobAdvertisementService jobAdvertisementService;
    private final UserService userService;

    @GetMapping("comments/waiting")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<List<Comment>> getWaitingComments() {
        return ResponseEntity.ok(commentService.pendingApprovalComments());
    }


    @PostMapping("comments/{comment_id}/approve")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Comment> approveComment(@PathVariable Long comment_id) {
        return new ResponseEntity<>(commentService.approveComment(comment_id), HttpStatus.OK);
    }

    @PostMapping("comments/{comment_id}/reject")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Comment> rejectComment(@PathVariable Long comment_id) {
        return new ResponseEntity<>(commentService.rejectComment(comment_id), HttpStatus.OK);
    }

    @GetMapping("jobs/waiting")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<List<JobAdvertisement>> getWaitingJobs() {
        return ResponseEntity.ok(jobAdvertisementService.pendingApprovalJobs());
    }


    @PostMapping("jobs/approve")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Void> approveJobs(@RequestBody List<JobAdvertisement> comments) {
        jobAdvertisementService.approveJobs(comments);
        return ResponseEntity.ok().build();
    }

    @PostMapping("jobs/reject")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Void> rejectJobs(@RequestBody List<JobAdvertisement> comments) {
        jobAdvertisementService.rejectJobs(comments);
        return ResponseEntity.ok().build();
    }

    @GetMapping("users/active")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<User>> activeUsers() {
        return ResponseEntity.ok(userService.getAllActiveUsers());
    }

    @GetMapping("users/blocked")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<User>> rejectUser() {
        return ResponseEntity.ok(userService.getBlockedUsers());
    }

    @GetMapping("users/block")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> blockUser(@RequestParam Long userId) {
        userService.blockUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("users/unblock")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> unblockUser(@RequestParam Long userId) {
        userService.unblockUser(userId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("user/list")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/user/promote-moderator")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> makeModerator(@RequestParam Long userId) {
        userService.promoteToModerator(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/promote-user")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> makeUser(@RequestParam Long userId) {
        userService.promoteToEmployee(userId);
        return ResponseEntity.ok().build();
    }
}
