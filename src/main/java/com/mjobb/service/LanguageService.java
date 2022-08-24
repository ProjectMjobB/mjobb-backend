package com.mjobb.service;

import com.mjobb.entity.Language;
import com.mjobb.entity.Tag;

import java.util.List;

public interface LanguageService {
    List<Language> getAllLanguages();

    void saveLanguage(Language language);

    void deleteLanguage(Language language);

    Language getLanguageById(Long id);
}
