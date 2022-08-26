package com.mjobb.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table
public class Employee extends User {

    private String areaOfInterest;
    private String requestedWorkingType;
    private Long age;
    @Nullable
    private int yearsOfExperience;
    private BigDecimal currentSalary;
    private BigDecimal requestedSalary;
    @OneToMany
    private List<WorkingArea> workingAreas;

    @ManyToMany
    @JoinTable(name = "users_languages",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "languages_id")})
    private Set<Language> languages = new HashSet<>();


    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<JobAdvertisement> favoriteJobs;
    @OneToMany
    @JoinTable(name = "employee_applications",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "application_id")})
    private List<Application> applications;


    public void addLanguage(Language language) {
        this.languages.add(language);
        language.getEmployees().add(this);
    }

    public void removeLanguage(long langId) {
        Language language = this.languages.stream().filter(t -> t.getId() == langId).findFirst().orElse(null);
        if (language != null) {
            this.languages.remove(language);
            language.getEmployees().remove(this);
        }
    }

}
