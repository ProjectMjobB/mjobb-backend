package com.mjobb.service.impl;

import com.mjobb.entity.Comment;
import com.mjobb.entity.User;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.CommentRepository;
import com.mjobb.repository.UserRepository;
import com.mjobb.request.CommentRequest;
import com.mjobb.response.CommentResponse;
import com.mjobb.service.CommentService;
import com.mjobb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserService userService;

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }


    @Override
    public List<Comment> pendingApprovalComments() {
        return commentRepository.getAllByAcceptedAndRejected(false, false);
    }

    @Override
    public Comment approveComment(Long id) {
          Comment comment = commentRepository.findById(id).orElse(null);
            if (comment == null) {
                throw new WebServiceException("Comment not found");
            }
            comment.setAccepted(true);
            comment.setRejected(false);
            commentRepository.save(comment);
            return comment;
    }

    @Override
    public Comment rejectComment(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            throw new WebServiceException("Comment not found");
        }
        comment.setRejected(true);
        comment.setAccepted(false);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Comment updateComment(long toUserId,long id, CommentRequest commentRequest) {
        User ToUser = userService.getUserById(toUserId);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("CommentId " + id + "not found"));
                comment.setComment(commentRequest.getComment());
                comment.setToUser(ToUser);
                comment.setPoint(commentRequest.getPoint());
                commentRepository.save(comment);
                return comment;
    }

    @Override
    public List<CommentResponse> getAllCommentsByToUserId(Long userId) {

        List<CommentResponse> commentResponses = new ArrayList<>();
        if (userService.getUserById(userId) == null) {
            throw new WebServiceException("Not found User with id = " + userId);
        }
        List<Comment> comments = commentRepository.findByToUserId(userId);

        for (Comment comment : comments) {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setComment(comment.getComment());
            commentResponse.setPoint(comment.getPoint());
            User fromUser = userRepository.findById(comment.getFromUserId()).orElse(null);
            commentResponse.setFromUser(fromUser);
            commentResponse.setToUser(comment.getToUser());
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

    @Override
    public Comment createComment(@PathVariable(value = "toUserId") Long toUserId,
                                 @RequestBody Comment commentRequest) {
        Comment comment = userRepository.findById(toUserId).map(user -> {
            commentRequest.setToUser(user);
            commentRequest.setAccepted(false);
            commentRequest.setRejected(false);
            commentRequest.setFromUserId(userService.getCurrentUser().getId());
            calculateGeneralPoint(userService.getCurrentUser().getId());
            return commentRepository.save(commentRequest);
        }).orElseThrow(() -> new WebServiceException("Not found User with id = " + toUserId));
     return comment;
    }

    @Override
    public Comment getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("Not found Comment with id = " + id));
        return comment;
    }

    @Override
    public List<Comment> getAllCommentsByFromUserId(Long userId) {
        if (userService.getUserById(userId) == null) {
            throw new WebServiceException("Not found User with id = " + userId);
        }
        List<Comment> comments = commentRepository.findByFromUserId(userId);
        return comments;
    }

    @Override
    public List<CommentResponse> getAcceptedCommentsByToUserId(Long userId) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        if (userService.getUserById(userId) == null) {
            throw new WebServiceException("Not found User with id = " + userId);
        }
        List<Comment> comments = commentRepository.findByToUserId(userId);

        for (Comment comment : comments) {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setComment(comment.getComment());
            commentResponse.setPoint(comment.getPoint());
            User fromUser = userRepository.findById(comment.getFromUserId()).orElse(null);
            commentResponse.setFromUser(fromUser);
            commentResponse.setToUser(comment.getToUser());
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

    @Override
    public List<Comment> getAcceptedCommentsByFromUserId(Long userId) {
        if (userService.getUserById(userId) == null) {
            throw new WebServiceException("Not found User with id = " + userId);
        }
        List<Comment> comments = commentRepository.findByFromUserIdAndAccepted(userId,true);
        return comments;
    }

    @Override
    public List<Comment> getAllComments() {

        return commentRepository.findAll();
    }

    private void calculateGeneralPoint(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new WebServiceException("Not found User with id = " + userId));

        List<Comment> comments = commentRepository.findByFromUserId(userId);

        if (CollectionUtils.isEmpty(comments)) {
            return;
        }

        double total = comments.stream().mapToDouble(Comment::getPoint).sum();
        double count = comments.size();
        user.setGeneralPoint((total / count));
        userRepository.save(user);
    }

}
