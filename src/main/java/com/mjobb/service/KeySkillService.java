package com.mjobb.service;

import com.mjobb.entity.KeySkill;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface KeySkillService {
    KeySkill createKeySkill(Long resumeId,
                            @RequestBody KeySkill keySkillRequest);

    KeySkill updateKeySkill(long id, KeySkill keySkillRequest);

    void deleteKeySkill(long id);

    List<KeySkill> getAllKeySkillsByResumeId(Long resumeId);
}
