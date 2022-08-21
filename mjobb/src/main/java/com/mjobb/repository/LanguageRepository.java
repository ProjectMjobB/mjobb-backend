package com.mjobb.repository;

import com.mjobb.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {


    Language findByNameIgnoreCase(String name);
}
