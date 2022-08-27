package com.mjobb.controller;

import com.mjobb.entity.Tag;
import com.mjobb.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import com.mjobb.entity.Category;
import com.mjobb.entity.JobAdvertisement;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tags/")
@RequiredArgsConstructor
@ApiOperation("Tags API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping("/tags/{tagId}/jobAdvertisements")
    public ResponseEntity<List<JobAdvertisement>> getJobsFromTagId(@PathVariable(value = "tagId") Long tagId) {
        return ResponseEntity.ok(tagService.getJobsFromTagId(tagId));
    }

    @GetMapping("/jobAdvertisements/{jobAdvertisementId}/tags")
    public ResponseEntity<List<Tag>> getAllTagsByJobAdvertisementId(@PathVariable(value = "jobAdvertisementId") Long jobAdvertisementId) {

        return new ResponseEntity<>(tagService.getAllTagsByJobAdvertisementId(jobAdvertisementId), HttpStatus.OK);
    }
}
