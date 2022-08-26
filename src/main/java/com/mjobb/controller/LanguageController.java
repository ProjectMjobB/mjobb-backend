package com.mjobb.controller;

import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.Language;
import com.mjobb.service.LanguageService;
import com.mjobb.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/languages/")
@RequiredArgsConstructor
@ApiOperation("Languages API")
public class LanguageController {
    private final LanguageService languageService;
    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {

        return new ResponseEntity<>(languageService.getAllLanguages(), HttpStatus.OK);
    }

    @GetMapping("/jobAdvertisements/{jobId}/languages-by-id")
    public ResponseEntity<List<Language>> getAllLanguagesByJobAdvertisementId(@PathVariable(value = "jobId") Long jobId) {
        return new ResponseEntity<>(languageService.getAllLanguagesByJobAdvertisementId(jobId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguagesById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(languageService.getLanguagesById(id), HttpStatus.OK);
    }

    @GetMapping("/{languageId}/jobs")
    public ResponseEntity<List<JobAdvertisement>> getAllJobsByLanguageId(@PathVariable(value = "languageId") Long languageId) {
        return new ResponseEntity<>(languageService.getAllJobsByLanguageId(languageId), HttpStatus.OK);
    }
    @PostMapping("/jobAdvertisement/{jobId}/{languageId}")
    public ResponseEntity<Language> addLanguage(@PathVariable(value = "jobId") Long jobId,@PathVariable(value = "languageId") Long languageId) {
        return new ResponseEntity<>(languageService.addLanguage(jobId,languageId), HttpStatus.CREATED);
    }

    @DeleteMapping("/jobAdvertisement/{jobId}/languages/{languageId}")
    public ResponseEntity<HttpStatus> deleteLanguageFromJob(@PathVariable(value = "jobId") Long jobId, @PathVariable(value = "languageId") Long languageId) {
        languageService.deleteLanguageFromJob(jobId, languageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLanguage(@PathVariable("id") long id) {
        languageService.deleteLanguage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
