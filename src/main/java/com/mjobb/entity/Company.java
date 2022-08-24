package com.mjobb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Company extends User {

    private String website;
    @Size(min = 4,max = 4,  message = "Foundation year must be 4 digits")
    private String foundationDate;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "company_jobs",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "job_advertisement_id")})
    private List<JobAdvertisement> jobs;
}
