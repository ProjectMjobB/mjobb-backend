package com.mjobb.controller;

import com.mjobb.entity.Category;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Tag;
import com.mjobb.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tags/")
@RequiredArgsConstructor
@ApiOperation("Tags API")
public class TagController {

    private final TagService tagService;


    @GetMapping("/all")
    public ResponseEntity<List<Tag>> allTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping("/save")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Tag> saveTag(@RequestBody Tag tag) {
        tagService.saveTag(tag);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteTag(@RequestBody Tag tag) {
        tagService.deleteTag(tag);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<List<JobAdvertisement>> getJobsFromCategory(@RequestParam Long id) {
        Tag tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag.getJobs());
    }
}
