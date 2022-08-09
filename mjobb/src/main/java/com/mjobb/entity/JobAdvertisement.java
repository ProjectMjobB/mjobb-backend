package com.mjobb.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class JobAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String title;
    private String category;
    private String address;
    private Long yearsOfExperience;
    private BigDecimal minimumSalary;
    private BigDecimal maximumSalary;
    @Lob
    private byte[] file;
    private String type;
    private String workingType;
    @OneToMany
    private List<Application> applications;
    @ManyToOne
    private Company company;
    @OneToMany
    private List<Comment> comments;

}
