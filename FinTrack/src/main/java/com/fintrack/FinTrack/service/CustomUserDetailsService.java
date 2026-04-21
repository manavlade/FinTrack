package com.fintrack.FinTrack.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fintrack.FinTrack.models.UserModel;
import com.fintrack.FinTrack.repository.UserRepository;

public class CustomUserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        UserModel user = userRepository.findByEmployeeEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return User.builder().username(user.getEmail()).password(user.getPassword()).roles(user.getEmployeeRole()).build();
    }

}
