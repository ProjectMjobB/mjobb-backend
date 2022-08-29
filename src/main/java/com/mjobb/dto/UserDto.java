package com.mjobb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserDto {
    @ApiModelProperty(notes = "User first name", example = "Alex")
    private String firstname;
    @ApiModelProperty(notes = "User surname", example = "de Souza")
    private String surname;
    @ApiModelProperty(notes = "Profile Image", example = "Profile Image", required = false)
    private String profileImage;
    @ApiModelProperty(notes = "User phone number", example = "5553332211")
    private String phoneNumber;
    @ApiModelProperty(notes = "User profile image", example = "image Bytes")
    private String country;
    @ApiModelProperty(notes = "User about", example = "Something about the user")
    private String about;
    @ApiModelProperty(notes = "User contact", example = "User contact information")
    private String contactInformation;
    @ApiModelProperty(notes = "City", example = "User city information", required = false)
    private String city;
    @ApiModelProperty(notes = "Company website", example = "User website information", required = false)
    private String website;
    @ApiModelProperty(notes = "Company fd", example = "User fd information", required = false)
    private String foundationDate;
    @ApiModelProperty(notes = "Profile Image", example = "Profile Image", required = false)
    private int yearsOfExperience;
    @ApiModelProperty(notes = "Profile Image", example = "Profile Image", required = false)
    private BigDecimal minimumSalary;
    @ApiModelProperty(notes = "Profile Image", example = "Profile Image", required = false)
    private BigDecimal maximumSalary;
    @ApiModelProperty(notes = "Profile Image", example = "Profile Image", required = false)
    private String workingArea;
    @ApiModelProperty(notes = "Profile Image", example = "Profile Image", required = false)
    private Long age;

    @ApiModelProperty(notes = "Company point", example = "User point information", required = false)
    private Double generalPoint;
}


