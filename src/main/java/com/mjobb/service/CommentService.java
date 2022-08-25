package com.mjobb.service;

import com.mjobb.entity.Comment;
import com.mjobb.request.CommentRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommentService {
    void userCommentToUser(CommentRequest request);

    List<Comment> pendingApprovalComments();

    void approveComments(List<Comment> comments);

    void rejectComments(List<Comment> comments);

    Comment updateComment(long id, @RequestBody Comment commentRequest);
    void deleteComment(long id);
    List<Comment> getAllCommentsByToUserId(Long userId);

    Comment getCommentById(Long id);
}
