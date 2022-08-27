package com.mjobb.controller;

import com.mjobb.entity.Complaint;
import com.mjobb.request.ComplaintRequest;
import com.mjobb.service.ComplaintService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/complaints/")
@RequiredArgsConstructor
@ApiOperation("Comments API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping("user-to-user")
    @Secured({"ROLE_EMPLOYEE", "ROLE_COMPANY"})
    public ResponseEntity<Complaint> userComplaintToCompany(@RequestBody ComplaintRequest request) {
        return new ResponseEntity<>(complaintService.userComplaintToUser(request), HttpStatus.OK);
    }


    @PostMapping("user-to-job")
    @Secured({"ROLE_EMPLOYEE"})
    public ResponseEntity<Complaint> userComplaintToJob(@RequestBody ComplaintRequest request) {
        return new ResponseEntity<>(complaintService.userComplaintToJob(request), HttpStatus.OK);
    }
}
