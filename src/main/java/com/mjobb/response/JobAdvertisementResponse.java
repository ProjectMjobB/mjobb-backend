package com.mjobb.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjobb.entity.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class JobAdvertisementResponse {
    private Long id;
    private String title;
    private String address;
    private Long yearsOfExperience;
    private BigDecimal minimumSalary;
    private BigDecimal maximumSalary;
    private String file;
    private String description;
    private JobType jobType;
    private String workingType;
    private String country;
    private String city;
    private List<Application> applications;
    private Company company;
    private List<Complaint> complaints;
    private boolean accepted;
    private Date createdDate;
    private Date updatedDate;
    private Set<Tag> tags;
    private Category category;
    private Set<Language> languages;
}
