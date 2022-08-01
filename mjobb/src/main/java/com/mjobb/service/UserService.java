package com.mjobb.service;

import com.mjobb.request.SignUpRequest;

public interface UserService {

    boolean existByEmail(String email);

    void registerUser(SignUpRequest request);

}
