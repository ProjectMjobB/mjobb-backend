package com.mjobb.repository;

import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Language;
import com.mjobb.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language findByNameIgnoreCase(String name);
    List<Language> findLanguageByJobAdvertisementsId(Long jobId);

}
