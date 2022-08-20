package com.mjobb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @ApiModelProperty(notes = "User first name", example = "Alex")
    private String firstname;
    @ApiModelProperty(notes = "User last name", example = "de Souza")
    private String lastName;
    @ApiModelProperty(notes = "User phone number", example = "5553332211")
    private String phoneNumber;
    @ApiModelProperty(notes = "User profile image", example = "image Bytes")
    private Byte[] profileImage;
    @ApiModelProperty(notes = "User country", example = "Brazil")
    private String country;
    @ApiModelProperty(notes = "User about", example = "Something about the user")
    private String about;
    @ApiModelProperty(notes = "User contact", example = "User contact information")
    private String contactInformation;
    @ApiModelProperty(notes = "Company city", example = "User contact information", required = false)
    private String city;
}
