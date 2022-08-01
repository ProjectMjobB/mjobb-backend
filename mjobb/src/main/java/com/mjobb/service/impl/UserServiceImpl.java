package com.mjobb.service.impl;

import com.mjobb.entity.User;
import com.mjobb.repository.UserRepository;
import com.mjobb.request.SignUpRequest;
import com.mjobb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void registerUser(SignUpRequest request) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setFirstName(request.getFirstname());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(encoder.encode(request.getPassword()));

        userRepository.save(user);
    }
}
