package com.mjobb.controller;

import com.mjobb.entity.Comment;
import com.mjobb.service.CommentService;
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

    @GetMapping("comments/waiting")
    public ResponseEntity<List<Comment>> getWaitingComments() {
        return ResponseEntity.ok(commentService.pendingApprovalComments());
    }


    @PostMapping("comments/approve")
    public ResponseEntity<Void> approveComments(@RequestBody List<Comment> comments) {
        commentService.approveComments(comments);
        return ResponseEntity.ok().build();
    }
}
