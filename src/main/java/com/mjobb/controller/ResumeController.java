package com.mjobb.controller;

import com.mjobb.entity.Resume;
import com.mjobb.service.ResumeService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/resume")
@AllArgsConstructor
@ApiOperation("Comments API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ResumeController {
    ResumeService resumeService;

    @GetMapping()
    public ResponseEntity<Resume> getResume() {
        return null;
    }
    @GetMapping("/{empId}")
    public ResponseEntity<Resume> getResumeByEmpId(@PathVariable("empId") long empId) {

        return new ResponseEntity<>(resumeService.getResumeByEmpId(empId), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable("id") long id,@RequestBody Resume resumeRequest) {
        return new ResponseEntity<>(resumeService.updateResume(id,resumeRequest), HttpStatus.OK);
    }
}
