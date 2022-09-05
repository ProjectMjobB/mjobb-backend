package com.mjobb.repository;

import com.mjobb.entity.Resume;
import com.mjobb.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResumeRepository  extends JpaRepository<Resume, Long> {
    Resume findByUserId(long id);
}
