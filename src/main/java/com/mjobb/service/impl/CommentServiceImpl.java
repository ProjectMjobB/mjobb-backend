package com.mjobb.service.impl;

import com.mjobb.entity.Comment;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.User;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.CommentRepository;
import com.mjobb.repository.JobAdvertisementRepository;
import com.mjobb.request.CommentRequest;
import com.mjobb.service.CommentService;
import com.mjobb.service.JobAdvertisementService;
import com.mjobb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final CommentRepository commentRepository;


    @Override
    public void userCommentToUser(CommentRequest request) {
        User fromUser = userService.getCurrentUser();
        User toUser = userService.getUserById(request.getToUserId());
        Comment comment = Comment.builder()
                .comment(request.getComment())
                .toUser(toUser)
                .fromUser(fromUser)
                .point(request.getPoint())
                .build();

        List<Comment> comments = CollectionUtils.isEmpty(toUser.getComments()) ? new ArrayList<>() : toUser.getComments();
        List<Comment> commentHistories = CollectionUtils.isEmpty(fromUser.getComments()) ? new ArrayList<>() : fromUser.getComments();

        comments.add(comment);
        commentHistories.add(comment);

        fromUser.setComments(commentHistories);
        toUser.setComments(comments);


        calculateGeneralPoint(toUser);

        userService.save(toUser);
        userService.save(fromUser);

    }

    public Comment updateComment(long id, @RequestBody Comment commentRequest) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("CommentId " + id + "not found"));
        comment.setComment(commentRequest.getComment());
        comment.setPoint(commentRequest.getPoint());
        comment.setToUser(commentRequest.getToUser());
        comment.setFromUser(commentRequest.getFromUser());
        return commentRepository.save(comment);
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> pendingApprovalComments() {
        return commentRepository.getAllByAcceptedAndRejected(false, false);
    }

    @Override
    public void approveComments(List<Comment> comments) {
        if (CollectionUtils.isEmpty(comments)) {
            return;
        }
        comments.forEach(comment -> {
            comment.setAccepted(true);
            commentRepository.save(comment);
        });
    }

    @Override
    public void rejectComments(List<Comment> comments) {
        if (CollectionUtils.isEmpty(comments)) {
            return;
        }

        comments.forEach(comment -> {
                    comment = commentRepository.findById(comment.getId()).orElseThrow();
                    comment.setRejected(true);
                    commentRepository.save(comment);
                }
        );
    }

    @Override
    public List<Comment> getAllCommentsByToUserId(Long userId) {
        if (userService.getUserById(userId) == null) {
            throw new WebServiceException("Not found User with id = " + userId);
        }
        List<Comment> comments = commentRepository.findByToUserId(userId);
        return comments;
    }

    @Override
    public Comment getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("Not found Comment with id = " + id));
        return comment;
    }

    private void calculateGeneralPoint(User user) {
        if (CollectionUtils.isEmpty(user.getComments())) {
            return;
        }

        List<Comment> comments = user.getComments();

        long total = comments.stream().mapToLong(Comment::getPoint).sum();
        int count = comments.size();
        user.setGeneralPoint((double) (total / count));
    }

}
