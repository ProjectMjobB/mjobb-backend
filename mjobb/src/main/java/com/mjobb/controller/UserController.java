package com.mjobb.controller;

import com.mjobb.dto.UserDto;
import com.mjobb.request.ChangePasswordRequest;
import com.mjobb.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/users/")
@RequiredArgsConstructor
@ApiOperation("Users API")
public class UserController {

    private final UserService userService;

    @PostMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto user) {
        userService.updateUser(user);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.accepted().build();
    }

}
