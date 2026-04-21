package com.fintrack.FinTrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fintrack.FinTrack.models.UserModel;
import com.fintrack.FinTrack.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserModel userModel) {
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

}
