package com.mjobb.repository;

import com.mjobb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByAcceptedAndRejected(boolean accepted, boolean rejected);
}
