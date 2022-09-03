package com.mjobb.response;

import com.mjobb.entity.Application;
import com.mjobb.entity.Company;
import lombok.Data;

import java.util.Optional;

@Data
public class ApplicationResponse {
    Application application;
    Optional<Company> company;
}
