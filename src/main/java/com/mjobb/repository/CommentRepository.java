package com.mjobb.repository;

import com.mjobb.entity.Comment;
import com.mjobb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByAcceptedAndRejected(boolean accepted, boolean rejected);
    List<Comment> findByToUserId(Long toUserId);
    List<Comment> findByFromUserId(Long fromUserId);

    List<Comment> findByToUserIdAndAccepted(Long toUserId,boolean accepted);
    List<Comment> findByFromUserIdAndAccepted(Long fromUserId,boolean accepted);

    List<Comment> findAllByToUserId(Long toUserId);
    List<Comment> findAllByFromUserId(Long fromUserId);
    @Transactional
    void deleteByFromUserId(long toUserId);

    @Transactional
    void deleteByToUserId(long fromUserId);
}
