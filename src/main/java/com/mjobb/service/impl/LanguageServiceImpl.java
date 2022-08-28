package com.mjobb.service.impl;

import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Language;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.JobAdvertisementRepository;
import com.mjobb.repository.LanguageRepository;
import com.mjobb.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final JobAdvertisementRepository jobAdvertisementRepository;


    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public void saveLanguage(Language language) {
        languageRepository.save(language);
    }

    @Override
    public void deleteLanguage(long id) {
        languageRepository.deleteById(id);
    }

    @Override
    public Language getLanguageById(Long id) {
        return languageRepository.findById(id).orElseThrow(() -> new WebServiceException("Language not found this id: " + id));
    }
    public List<Language> getAllLanguagesByJobAdvertisementId(Long jobId) {
        if (!jobAdvertisementRepository.existsById(jobId)) {
            throw new WebServiceException("Not found Job with id = " + jobId);
        }
        List<Language> languages = languageRepository.findLanguageByJobAdvertisementsId(jobId);

        return languages;
    }

    public Language getLanguagesById(Long id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new WebServiceException("Not found Language with id = " + id));

        return language;
    }

    public List<JobAdvertisement> getAllJobsByLanguageId(Long languageId) {
        if (!languageRepository.existsById(languageId)) {
            throw new WebServiceException("Not found Language with id = " + languageId);
        }
        List<JobAdvertisement> jobs = jobAdvertisementRepository.findJobAdvertisementByLanguagesId(languageId);

        return jobs;
    }

    @Override
    public Language addLanguage(Long jobId, Long languageId) {
        Language language = jobAdvertisementRepository.findById(jobId).map(job -> {
            long langId = languageId;
            Language languageRequest = languageRepository.findById(langId).orElseThrow(() -> new WebServiceException("Not found Language with id = " + langId));

            // tag is existed
            if (langId != 0L) {
                Language _language = languageRepository.findById(langId)
                        .orElseThrow(() -> new WebServiceException("Not found Language with id = " + langId));
                job.addLanguage(_language);
                jobAdvertisementRepository.save(job);
                return _language;
            }

            // add and create new Tag
            job.addLanguage(languageRequest);
            return languageRepository.save(languageRequest);
        }).orElseThrow(() -> new WebServiceException("Not found Job with id = " + jobId));

        return language;
    }

    public void deleteLanguageFromJob(@PathVariable(value = "jobId") Long jobId, @PathVariable(value = "languageId") Long languageId) {
        JobAdvertisement job = jobAdvertisementRepository.findById(jobId)
                .orElseThrow(() -> new WebServiceException("Not found Job with id = " + jobId));

        job.removeLanguage(languageId);
        jobAdvertisementRepository.save(job);
    }

    @Override
    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }
}
