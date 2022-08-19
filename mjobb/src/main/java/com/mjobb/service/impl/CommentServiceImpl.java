package com.mjobb.service.impl;

import com.mjobb.entity.Comment;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.User;
import com.mjobb.repository.CommentRepository;
import com.mjobb.request.CommentRequest;
import com.mjobb.service.CommentService;
import com.mjobb.service.JobAdvertisementService;
import com.mjobb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final JobAdvertisementService jobAdvertisementService;
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

    @Override
    public void userCommentToJob(CommentRequest request) {
        User fromUser = userService.getCurrentUser();
        JobAdvertisement jobAdvertisement = jobAdvertisementService.getJobAdvertisementById(request.getJobId());
        Comment comment = Comment.builder()
                .comment(request.getComment())
                .job(jobAdvertisement)
                .fromUser(fromUser)
                .point(request.getPoint())
                .build();


        List<Comment> commentHistories = CollectionUtils.isEmpty(fromUser.getComments()) ? new ArrayList<>() : fromUser.getComments();
        commentHistories.add(comment);
        fromUser.setComments(commentHistories);
        userService.save(fromUser);

        List<Comment> comments = CollectionUtils.isEmpty(jobAdvertisement.getComments()) ? new ArrayList<>() : jobAdvertisement.getComments();
        comments.add(comment);
        jobAdvertisement.setComments(comments);
        jobAdvertisementService.save(jobAdvertisement);
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
