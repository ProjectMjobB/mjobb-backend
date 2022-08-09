package com.mjobb.service.impl;

import com.mjobb.entity.Comment;
import com.mjobb.entity.User;
import com.mjobb.request.CommentRequest;
import com.mjobb.service.CommentService;
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


    @Override
    public void userCommentToUser(CommentRequest request) {
        User fromUser = userService.getCurrentUser();
        User toUser = userService.getUserById(request.getFromUserId());
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
