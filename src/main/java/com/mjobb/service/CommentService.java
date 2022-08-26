package com.mjobb.service;

import com.mjobb.entity.Comment;
import com.mjobb.request.CommentRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommentService {

    List<Comment> pendingApprovalComments();

    Comment approveComment(Long id);

    Comment rejectComment(Long id);

    Comment updateComment(long toUserId,long id, @RequestBody CommentRequest commentRequest);
    void deleteComment(long id);
    List<Comment> getAllCommentsByToUserId(Long userId);

    Comment createComment(@PathVariable(value = "toUserId") Long toUserId,
                           @RequestBody Comment commentRequest);

    Comment getCommentById(Long id);

    List<Comment> getAllCommentsByFromUserId(Long userId);

    List<Comment> getAcceptedCommentsByToUserId(Long userId);

    List<Comment> getAcceptedCommentsByFromUserId(Long userId);

}
