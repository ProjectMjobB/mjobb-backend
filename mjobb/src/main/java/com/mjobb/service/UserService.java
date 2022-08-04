package com.mjobb.service;

import com.mjobb.dto.UserDto;
import com.mjobb.entity.User;
import com.mjobb.request.ChangePasswordRequest;
import com.mjobb.request.SignUpRequest;

public interface UserService {

    boolean existByEmail(String email);

    void registerUser(SignUpRequest request);

    User getCurrentUser();

    void updateUser(UserDto userDto);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    boolean checkIfValidOldPassword(final User user, final String oldPassword);

}
