package com.mjobb.repository;

import com.mjobb.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findTagsByJobAdvertisementsId(Long jobAdvertisementId);

}
