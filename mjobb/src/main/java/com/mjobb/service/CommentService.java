package com.mjobb.service;

import com.mjobb.request.CommentRequest;

public interface CommentService {


    void userCommentToUser(CommentRequest request);

    void userCommentToJob(CommentRequest request);

}
