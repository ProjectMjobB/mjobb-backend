package com.mjobb.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Employee extends User {

    private String areaOfInterest;
    private String workingArea;
    private Long age;
    private BigDecimal currentSalary;
    private BigDecimal requestedSalary;
    @OneToMany
    private List<WorkingArea> workingAreas;
    @OneToMany
    private List<Language> languages;
    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<JobAdvertisement> favoriteJobs;

}
