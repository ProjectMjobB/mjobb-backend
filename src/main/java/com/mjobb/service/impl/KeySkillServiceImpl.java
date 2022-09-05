package com.mjobb.service.impl;

import com.mjobb.entity.KeySkill;
import com.mjobb.repository.KeySkillRepository;
import com.mjobb.repository.ResumeRepository;
import com.mjobb.service.KeySkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeySkillServiceImpl implements KeySkillService {
    @Autowired
    KeySkillRepository keySkillRepository;
    @Autowired
    ResumeRepository resumeRepository;
    @Override
    public KeySkill createKeySkill(Long resumeId,
                                   @RequestBody KeySkill keySkillRequest) {
        KeySkill keySkill = resumeRepository.findById(resumeId).map(resume -> {
            keySkillRequest.setResume(resume);
            return keySkillRepository.save(keySkillRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Resume with id = " + resumeId));
        return keySkill;
    }

    @Override
    public KeySkill updateKeySkill(long id, KeySkill keySkillRequest) {
        KeySkill keySkill = keySkillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KeySkillId " + id + "not found"));
        keySkill.setName(keySkillRequest.getName());
        keySkillRepository.save(keySkill);
        return keySkill;
    }

    @Override
    public List<KeySkill> deleteKeySkill(long id) {
        keySkillRepository.deleteById(id);
        return keySkillRepository.findAll();
    }

    @Override
    public List<KeySkill> getAllKeySkillsByResumeId(Long resumeId) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new ResourceNotFoundException("Not found Resume with id = " + resumeId);
        }
        List<KeySkill> keySkills = keySkillRepository.findByResumeId(resumeId);
        return keySkills;
    }

    @Override
    public KeySkill getKeySkillById(long id) {
        KeySkill keySkill = keySkillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KeySkillId " + id + "not found"));
        return keySkill;
    }
}
