package com.mjobb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinTable(name = "company_jobs",
            joinColumns = {@JoinColumn(name = "job_advertisement_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")})
    private Company company;
    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<Complaint> complaints;
    private boolean accepted;
    private Date createdDate;
    private Date updatedDate;

}
