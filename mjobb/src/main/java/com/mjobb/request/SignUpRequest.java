package com.mjobb.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String password;
    private String firstname;
    private String surname;
    private String phoneNumber;
}

