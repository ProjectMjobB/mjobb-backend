package com.mjobb.service.impl;

import com.mjobb.entity.Language;
import com.mjobb.exception.WebServiceException;
import com.mjobb.repository.LanguageRepository;
import com.mjobb.repository.TagRepository;
import com.mjobb.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;


    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public void saveLanguage(Language language) {
        languageRepository.save(language);
    }

    @Override
    public void deleteLanguage(Language language) {
        languageRepository.delete(language);
    }

    @Override
    public Language getLanguageById(Long id) {
        return languageRepository.findById(id).orElseThrow(() -> new WebServiceException("Language not found this id: " + id));

    }
}
