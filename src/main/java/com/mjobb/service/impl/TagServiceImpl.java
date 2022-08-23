package com.mjobb.service.impl;

import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Tag;
import com.mjobb.repository.JobAdvertisementRepository;
import com.mjobb.repository.TagRepository;
import com.mjobb.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.mjobb.exception.WebServiceException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final JobAdvertisementRepository jobAdvertisementRepository;

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }
    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new WebServiceException("Tag not found this id: " + id));
    }

    @Override
    public List<JobAdvertisement> getJobsFromTagId(Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new WebServiceException("Not found Tag  with id = " + tagId);
        }
        List<JobAdvertisement> jobAdvertisements = jobAdvertisementRepository.findJobAdvertisementByTagsId(tagId);
        return jobAdvertisements;
    }
}
