package com.mjobb.repository;

import com.mjobb.entity.KeySkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface KeySkillRepository extends JpaRepository<KeySkill, Long> {

    List<KeySkill> findByResumeId(Long resumeId);
}
