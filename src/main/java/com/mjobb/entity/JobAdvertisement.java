package com.mjobb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobAdvertisement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String title;
    private String address;
    private Long yearsOfExperience;
    private BigDecimal minimumSalary;
    private BigDecimal maximumSalary;
    @Lob
    private byte[] file;
    private String type;
    private String workingType;
    private String country;
    private String city;
    @JsonIgnore
    @OneToMany
    @JoinTable(name = "job_applications",
            joinColumns = {@JoinColumn(name = "job_advertisement_id")},
            inverseJoinColumns = {@JoinColumn(name = "application_id")})
    private List<Application> applications;

    @ManyToOne
    @JoinTable(name = "company_jobs",
            joinColumns = {@JoinColumn(name = "job_advertisement_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")})
    private Company company;
    @OneToMany
    @JoinTable(name = "job_comments",
            joinColumns = {@JoinColumn(name = "job_advertisement_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private List<Comment> comments;
    @OneToMany
    @JoinTable(name = "job_complaints",
            joinColumns = {@JoinColumn(name = "job_advertisement_id")},
            inverseJoinColumns = {@JoinColumn(name = "complaint_id")})
    private List<Complaint> complaints;
    private boolean accepted;
    private Date createdDate;
    private Date updatedDate;
    @ManyToMany
    @JoinTable(name = "job_tags",
            joinColumns = {@JoinColumn(name = "job_advertisement_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

}
