package com.mjobb.controller;

import com.mjobb.request.ComplaintRequest;
import com.mjobb.service.ComplaintService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/complaints/")
@RequiredArgsConstructor
@ApiOperation("Comments API")
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping("user-to-user")
    public ResponseEntity<Void> userComplaintToCompany(@RequestBody ComplaintRequest request) {
        complaintService.userComplaintToUser(request);
        return ResponseEntity.ok().build();
    }


    @PostMapping("user-to-job")
    public ResponseEntity<Void> userComplaintToJob(@RequestBody ComplaintRequest request) {
        complaintService.userComplaintToJob(request);
        return ResponseEntity.ok().build();
    }
}
