package com.mjobb.controller;

import com.mjobb.entity.Comment;
import com.mjobb.request.CommentRequest;
import com.mjobb.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/comments/")
@RequiredArgsConstructor
@ApiOperation("Comments API")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByToUserId(@PathVariable(value = "userId") Long userId) {
        return new ResponseEntity<>(commentService.getAllCommentsByToUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @PostMapping("user-to-user")
    @Secured({"ROLE_EMPLOYEE", "ROLE_COMPANY"})
    public ResponseEntity<Void> userCommentToCompany(@RequestBody CommentRequest request) {
        commentService.userCommentToUser(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {

        return new ResponseEntity<>(commentService.updateComment(id,commentRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
