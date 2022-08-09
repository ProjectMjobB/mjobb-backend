package com.mjobb.controller;

import com.mjobb.request.CommentRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/comments/")
@RequiredArgsConstructor
@ApiOperation("Comments API")
public class CommentController {

    @PostMapping("user-to-user")
    public ResponseEntity<Void> userCommentToCompany(@RequestBody CommentRequest request){

        return ResponseEntity.ok().build();
    }


    @PostMapping("user-to-job")
    public ResponseEntity<Void> userCommentToJob(@RequestBody CommentRequest request){

        return ResponseEntity.ok().build();
    }


}
