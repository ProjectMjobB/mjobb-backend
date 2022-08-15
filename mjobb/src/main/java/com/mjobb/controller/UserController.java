package com.mjobb.controller;

import com.mjobb.dto.UserDto;
import com.mjobb.entity.Application;
import com.mjobb.entity.Employee;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.User;
import com.mjobb.request.ChangePasswordRequest;
import com.mjobb.service.ApplicationService;
import com.mjobb.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/users/")
@RequiredArgsConstructor
@ApiOperation("Users API")
public class UserController {

    private final UserService userService;
    private final ApplicationService applicationService;

    @PostMapping("update")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto user) {
        userService.updateUser(user);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("change-password")
    @Secured({"ROLE_EMPLOYEE", "ROLE_ADMIN", "ROLE_COMPANY", "ROLE_MODERATOR"})
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("my-applications")
    @Secured("ROLE_EMPLOYEE")
    public ResponseEntity<List<Application>> getJobApplications() {
        return ResponseEntity.accepted().body(applicationService.findAllApplicationForEmployee((Employee) userService.getCurrentUser()));
    }


    @GetMapping("my-favorites")
    @Secured("ROLE_EMPLOYEE")
    public ResponseEntity<List<JobAdvertisement>> getMyFavoriteJobs() {
        return ResponseEntity.accepted().body(((Employee) userService.getCurrentUser()).getFavoriteJobs());
    }


    @GetMapping("current-user")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.accepted().body(userService.getCurrentUser());
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return ResponseEntity.accepted().body(userService.getUserById(userId));
    }
}
