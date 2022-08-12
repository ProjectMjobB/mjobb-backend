package com.mjobb.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpRequest {

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @Size(min = 6, max = 15)
    private String password;
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @Pattern(regexp = "^1?(\\d{10})", message = "Phone number can only be numbers ")
    @Size(min = 10, max = 11)
    private String phoneNumber;
    private String role;
}

