package com.mjobb.service;

import com.mjobb.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags();

    void saveTag(Tag tag);

    void deleteTag(Tag tag);

    Tag getTagById(Long id);
}
