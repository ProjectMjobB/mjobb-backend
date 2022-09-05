package com.mjobb.service;

import com.mjobb.entity.CompanyHistory;

import java.util.List;

public interface CompanyHistoryService {
    CompanyHistory createCompanyHistory(Long resumeId, CompanyHistory companyHistoryRequest);

    CompanyHistory updateCompanyHistory(long id, CompanyHistory companyHistoryRequest);

    List<CompanyHistory> deleteCompanyHistory(long id);

    List<CompanyHistory> getAllCompanyHistoriesByResumeId(Long resumeId);

    CompanyHistory getCompanyHistoryById(long id);
}
