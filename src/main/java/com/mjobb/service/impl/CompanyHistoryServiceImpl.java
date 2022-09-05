package com.mjobb.service.impl;

import com.mjobb.entity.CompanyHistory;
import com.mjobb.entity.KeySkill;
import com.mjobb.repository.CompanyHistoryRepository;
import com.mjobb.repository.KeySkillRepository;
import com.mjobb.repository.ResumeRepository;
import com.mjobb.service.CompanyHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyHistoryServiceImpl implements CompanyHistoryService {
    @Autowired
    CompanyHistoryRepository companyHistoryRepository;
    @Autowired
    ResumeRepository resumeRepository;
    @Override
    public CompanyHistory createCompanyHistory(Long resumeId, CompanyHistory companyHistoryRequest) {
        CompanyHistory companyHistory = resumeRepository.findById(resumeId).map(resume -> {
            companyHistoryRequest.setResume(resume);
            return companyHistoryRepository.save(companyHistoryRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Resume with id = " + resumeId));
        return companyHistory;
    }

    @Override
    public CompanyHistory updateCompanyHistory(long id, CompanyHistory companyHistoryRequest) {
        CompanyHistory companyHistory = companyHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company History " + id + "not found"));
        companyHistory.setName(companyHistoryRequest.getName());
        companyHistory.setStartYear(companyHistoryRequest.getStartYear());
        companyHistory.setEndYear(companyHistoryRequest.getEndYear());
        companyHistoryRepository.save(companyHistory);
        return companyHistory;
    }

    @Override
    public void deleteCompanyHistory(long id) {
        companyHistoryRepository.deleteById(id);
    }

    @Override
    public List<CompanyHistory> getAllCompanyHistoriesByResumeId(Long resumeId) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new ResourceNotFoundException("Not found Resume with id = " + resumeId);
        }
        List<CompanyHistory> companyHistories = companyHistoryRepository.findByResumeId(resumeId);
        return companyHistories;
    }

}
