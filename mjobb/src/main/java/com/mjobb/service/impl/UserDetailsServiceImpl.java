package com.mjobb.service.impl;


import com.mjobb.entity.Role;
import com.mjobb.entity.User;
import com.mjobb.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = this.userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (Role role : user.getRoles()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                grantedAuthorities.add(authority);
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }
    }

}
