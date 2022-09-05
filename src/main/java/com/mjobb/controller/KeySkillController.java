package com.mjobb.controller;

import com.mjobb.entity.KeySkill;
import com.mjobb.service.KeySkillService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/key-skill")
@RequiredArgsConstructor
@ApiOperation("Keys Skills API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class KeySkillController {
    @Autowired
    KeySkillService keySkillService;

    @PostMapping("/{resumeId}/create")
    public ResponseEntity<KeySkill> createKeySkill(@PathVariable(value = "resumeId") Long resumeId,
                                                  @RequestBody KeySkill keySkillRequest) {
        return new ResponseEntity<>(keySkillService.createKeySkill(resumeId,keySkillRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KeySkill> updateKeySkill(@PathVariable("id") long id, @RequestBody KeySkill keySkillRequest) {

        return new ResponseEntity<>(keySkillService.updateKeySkill(id,keySkillRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeySkill> getKeySkillById(@PathVariable("id") long id) {
        return new ResponseEntity<>(keySkillService.getKeySkillById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteKeySkill(@PathVariable("id") long id) {
        keySkillService.deleteKeySkill(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/resume/{resumeId}/key-skills")
    public ResponseEntity<List<KeySkill>> getAllKeySkillsByResumeId(@PathVariable(value = "resumeId") Long resumeId) {

        return new ResponseEntity<>(keySkillService.getAllKeySkillsByResumeId(resumeId), HttpStatus.OK);
    }

}
