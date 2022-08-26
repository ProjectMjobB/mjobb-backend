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

    @GetMapping("/users/{userId}/to-user/all-comments")
    public ResponseEntity<List<Comment>> getAllCommentsByToUserId(@PathVariable(value = "userId") Long userId) {
        return new ResponseEntity<>(commentService.getAllCommentsByToUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/from-user/all-comments")
    public ResponseEntity<List<Comment>> getAllCommentsByFromUserId(@PathVariable(value = "userId") Long userId) {
        return new ResponseEntity<>(commentService.getAllCommentsByFromUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/to-user/accepted-comments")
    public ResponseEntity<List<Comment>> getAcceptedCommentsByToUserId(@PathVariable(value = "userId") Long userId) {
        return new ResponseEntity<>(commentService.getAcceptedCommentsByToUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/from-user/accepted-comments")
    public ResponseEntity<List<Comment>> getAcceptedCommentsByFromUserId(@PathVariable(value = "userId") Long userId) {
        return new ResponseEntity<>(commentService.getAcceptedCommentsByFromUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @PutMapping("/{toUserId}/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("toUserId") long toUserId,@PathVariable("id") long id, @RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(commentService.updateComment(toUserId,id,commentRequest), HttpStatus.OK);
    }

    @Secured({"ROLE_EMPLOYEE", "ROLE_COMPANY","ROLE_ADMIN","ROLE_MODERATOR"})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Secured({"ROLE_EMPLOYEE", "ROLE_COMPANY"})
    @PostMapping("/users/{toUserId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "toUserId") Long toUserId,
                                                 @RequestBody Comment commentRequest) {

        return new ResponseEntity<>(commentService.createComment(toUserId,commentRequest), HttpStatus.CREATED);
    }


}
