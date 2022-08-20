package com.mjobb.service.impl;

import com.mjobb.dto.UserDto;
import com.mjobb.entity.Company;
import com.mjobb.entity.Employee;
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
import java.util.List;
import java.util.Objects;
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
        String requestRole = StringUtils.isEmpty(request.getRole()) ? RoleEnum.EMPLOYEE.code() : request.getRole();
        User user;
        if (RoleEnum.EMPLOYEE.code().equals(requestRole)) {
            user = new Employee();
        } else if (RoleEnum.COMPANY.code().equals(requestRole)) {
            user = new Company();
        } else {
            user = new User();
        }
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setFirstName(request.getFirstname());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


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
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Current user not found."));
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = getCurrentUser();
        PopulateUtils.copyNonNullProperties(userDto, user);

        if (Objects.nonNull(userDto.getProfileImage())){
            user.setProfileImage(user.getProfileImage());
        }


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

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void blockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));
        user.setEnabled(false);
        save(user);
    }

    @Override
    public void unblockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));
        user.setEnabled(true);
        save(user);
    }

    @Override
    public List<User> getBlockedUsers() {
        return userRepository.findAllByEnabled(false);
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findAllByEnabled(true);
    }

    @Override
    public List<User> getAllUsers() {
        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN.code()).orElseThrow(() -> {
            throw new WebServiceException("ADMIN role not found");
        });
        return userRepository.findAllByRolesNot(adminRole);
    }

    @Override
    public void promoteToModerator(Long userId) {
        Role moderator = roleRepository.findByName(RoleEnum.MODERATOR.code()).orElseThrow(() -> {
            throw new WebServiceException("MODERATOR role not found");
        });
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException("User not found");
        });

        Set<Role> roles = new HashSet<>();
        roles.add(moderator);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void promoteToEmployee(Long userId) {
        Role employee = roleRepository.findByName(RoleEnum.EMPLOYEE.code()).orElseThrow(() -> {
            throw new WebServiceException("EMPLOYEE role not found");
        });
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException("User not found");
        });

        Set<Role> roles = new HashSet<>();
        roles.add(employee);
        user.setRoles(roles);
        userRepository.save(user);
    }


    private void isValidRegisterRole(String role) {
        if (RoleEnum.ADMIN.code().equals(role) || RoleEnum.MODERATOR.code().equals(role)) {
            throw new WebServiceException("This role cannot be registered.");
        }
    }
}
