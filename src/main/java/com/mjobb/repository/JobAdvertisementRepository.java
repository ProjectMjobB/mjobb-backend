package com.mjobb.repository;

import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Long> {

    List<JobAdvertisement> findAllByAccepted(boolean accepted);
    List<JobAdvertisement> findJobAdvertisementByTagsId(Long tagId);
    List<JobAdvertisement> findByCategoryId(Long categoryId);

    List<JobAdvertisement> findJobAdvertisementByLanguagesId(Long languageId);

    List<JobAdvertisement> findByJobTypeId(Long postId);

    @Transactional
    void deleteByJobTypeId(long tutorialId);
    @Transactional
    void deleteByCategoryId(long categoryId);


}
