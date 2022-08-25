package com.mjobb.repository;

import com.mjobb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByAcceptedAndRejected(boolean accepted, boolean rejected);
    List<Comment> findByToUserId(Long toUserId);
    List<Comment> findByFromUserId(Long fromUserId);
    @Transactional
    void deleteByFromUserId(long toUserId);

    @Transactional
    void deleteByToUserId(long fromUserId);
}
