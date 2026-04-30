package com.fintrack.FinTrack.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintrack.FinTrack.exception.EmailAlreadyExistsException;
import com.fintrack.FinTrack.models.UserModel;
import com.fintrack.FinTrack.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel createUser(UserModel userModel) {

        if (userRepository.existsByEmployeeEmail(userModel.getEmployeeEmail())) {
            throw new EmailAlreadyExistsException("Email Already exists");
        }

        userModel.setEmployeePassword(
                passwordEncoder.encode(userModel.getEmployeePassword()));

        return userRepository.save(userModel);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmployeeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
