package com.mjobb.service.impl;

import com.mjobb.entity.Resume;
import com.mjobb.repository.ResumeRepository;
import com.mjobb.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    @Autowired
    ResumeRepository resumeRepository;

    @Override
    public Resume getResumeByEmpId(long id) {
        Resume resume = resumeRepository.findByUserId(id);
        return resume;
    }

    @Override
    public Resume updateResume(long id,Resume resumeRequest) {
        Resume _resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Resume with id = " + id));
        _resume.setTitle(resumeRequest.getTitle());
        _resume.setDescription(resumeRequest.getDescription());
        resumeRepository.save(_resume);
        return _resume;
    }


}
