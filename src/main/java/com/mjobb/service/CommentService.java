package com.mjobb.service;

import com.mjobb.entity.Comment;
import com.mjobb.request.CommentRequest;

import java.util.List;

public interface CommentService {


    void userCommentToUser(CommentRequest request);

    void userCommentToJob(CommentRequest request);

    List<Comment> pendingApprovalComments();

    void approveComments(List<Comment> comments);

}
