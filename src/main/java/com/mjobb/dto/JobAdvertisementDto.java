package com.mjobb.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class JobAdvertisementDto {

    private String title;
    private Long yearsOfExperience;
    private BigDecimal minimumSalary;
    private BigDecimal maximumSalary;
    private String file;
    private String type;
    private String workingType;
}
