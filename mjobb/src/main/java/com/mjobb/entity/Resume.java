package com.mjobb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String title;

    private String description;

    @Lob
    private byte[] cv;


    @OneToOne
    private Employee user;

    @OneToMany
    private List<KeySkill> keySkills;

    @OneToMany
    private List<CompanyHistory> companyHistories;

    @OneToMany
    private List<ProjectHistory> projectHistories;


}
