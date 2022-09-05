package com.mjobb.service;

import com.mjobb.entity.Resume;
import org.springframework.stereotype.Service;

@Service
public interface ResumeService {
    Resume getResumeByEmpId(long id);

    Resume updateResume(long id,String title,String description);
}
