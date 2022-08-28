package com.mjobb.service;

import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Language;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LanguageService {
    List<Language> getAllLanguages();

    void saveLanguage(Language language);

    void deleteLanguage(long id);

    Language getLanguageById(Long id);

    List<Language> getAllLanguagesByJobAdvertisementId(Long jobId);

    Language getLanguagesById(Long id);

    List<JobAdvertisement> getAllJobsByLanguageId(Long languageId);

    Language addLanguage(Long jobId, Long languageId);

    void deleteLanguageFromJob(@PathVariable(value = "jobId") Long jobId, @PathVariable(value = "languageId") Long languageId);

    Language createLanguage(Language language);
}
