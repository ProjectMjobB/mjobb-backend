package com.mjobb.controller;

import com.mjobb.dto.ApplicationDto;
import com.mjobb.dto.UserDto;
import com.mjobb.entity.Application;
import com.mjobb.entity.Employee;
import com.mjobb.mapper.CustomModelMapper;
import com.mjobb.request.ChangePasswordRequest;
import com.mjobb.service.ApplicationService;
import com.mjobb.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/users/")
@RequiredArgsConstructor
@ApiOperation("Users API")
public class UserController {

    private final UserService userService;
    private final ApplicationService applicationService;
    private final CustomModelMapper modelMapper;

    @PostMapping("update")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto user) {
        userService.updateUser(user);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("my-applications")
    public ResponseEntity<List<ApplicationDto>> getJobApplications() {
        List<Application> applications = applicationService.findAllApplicationForEmployee((Employee) userService.getCurrentUser());
        return ResponseEntity.accepted().body(modelMapper.mapList(applications, ApplicationDto.class));
    }

}
