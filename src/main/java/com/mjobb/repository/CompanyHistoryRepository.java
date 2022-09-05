package com.mjobb.repository;

import com.mjobb.entity.CompanyHistory;
import com.mjobb.entity.KeySkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyHistoryRepository extends JpaRepository<CompanyHistory, Long> {
    List<CompanyHistory> findByResumeId(Long resumeId);
}
