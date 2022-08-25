package com.mjobb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

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
    private String file;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JobType jobType;

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
    @JoinTable(name = "job_complaints",
            joinColumns = {@JoinColumn(name = "job_advertisement_id")},
            inverseJoinColumns = {@JoinColumn(name = "complaint_id")})
    private List<Complaint> complaints;
    private boolean accepted;
    private Date createdDate;
    private Date updatedDate;
    @ManyToMany
    @JoinTable(name = "job_tags",
            joinColumns = { @JoinColumn(name = "job_advertisement_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;



    @ManyToMany
    @JoinTable(name = "job_languages",
            joinColumns = { @JoinColumn(name = "job_advertisement_id") },
            inverseJoinColumns = { @JoinColumn(name = "language_id") })
    private Set<Language> languages = new HashSet<>();

    public void addLanguage(Language language) {
        this.languages.add(language);
        language.getJobAdvertisements().add(this);
    }

    public void removeLanguage(long languageId) {
        Language language = this.languages.stream().filter(t -> t.getId() == languageId).findFirst().orElse(null);
        if (language != null) {
            this.languages.remove(language);
            language.getJobAdvertisements().remove(this);
        }
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getJobAdvertisements().add(this);
    }

    public void removeTag(long tagId) {
        Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
        if (tag != null) {
            this.tags.remove(tag);
            tag.getJobAdvertisements().remove(this);
        }
    }

}
