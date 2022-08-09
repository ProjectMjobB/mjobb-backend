package com.mjobb.service.impl;

import com.mjobb.dto.UserDto;
import com.mjobb.entity.Role;
import com.mjobb.entity.User;
import com.mjobb.enums.RoleEnum;
import com.mjobb.exception.*;
import com.mjobb.repository.RoleRepository;
import com.mjobb.repository.UserRepository;
import com.mjobb.request.ChangePasswordRequest;
import com.mjobb.request.SignUpRequest;
import com.mjobb.service.UserService;
import com.mjobb.utils.PopulateUtils;
import com.mjobb.utils.SecurityUtils;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void registerUser(@NotNull SignUpRequest request) {
        if (existByEmail(request.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + request.getEmail());
        }
        isValidRegisterRole(request.getRole());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setFirstName(request.getFirstname());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        String requestRole = StringUtils.isEmpty(request.getRole()) ? RoleEnum.EMPLOYEE.code() : request.getRole();

        Role role = roleRepository.findByName(requestRole).orElseThrow(() -> new RoleNotFoundException("This role no found. -> " + RoleEnum.EMPLOYEE.code()));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityUtils.getCurrentUserEmail();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Current user not found."));
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = getCurrentUser();
        PopulateUtils.copyNonNullProperties(userDto, user);
        userRepository.save(user);
    }

    @Override
    public void changePassword(@NotNull ChangePasswordRequest changePasswordRequest) {
        User user = getCurrentUser();
        if (checkIfValidOldPassword(user, changePasswordRequest.getOldPassword())) {
            throw new WrongPasswordException("Wrong Password");
        }
        if (!StringUtils.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getNewPasswordValidate())) {
            throw new WrongPasswordException("Password does not match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }


    private void isValidRegisterRole(String role) {
        if (RoleEnum.ADMIN.code().equals(role) || RoleEnum.MODERATOR.code().equals(role)) {
            throw new WebServiceException("This role cannot be registered.");
        }
    }
}
