package com.mjobb.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Company extends User {

    private String website;
    private Date foundationDate;
    @OneToOne
    private GeneralPoint generalPoint;
    @OneToMany
    private List<JobAdvertisement> jobs;

}
