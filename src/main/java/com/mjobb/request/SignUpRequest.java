package com.mjobb.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpRequest {

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @Size(min = 3, max = 30, message = "Password must be between 3 and 30 characters")
    private String password;
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;
    private String surname;
    @Pattern(regexp = "^0?(\\d{10})", message = "Phone number can only be numbers ")
    private String phoneNumber;
    private String role;
}

