package com.mjobb.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class JobAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String title;
    private Long yearsOfExperience;
    private BigDecimal minimumSalary;
    private BigDecimal maximumSalary;
    @Lob
    private byte[] file;
    private String type;
    private String workingType;

}
