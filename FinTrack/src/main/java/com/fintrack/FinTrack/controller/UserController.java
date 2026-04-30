package com.fintrack.FinTrack.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintrack.FinTrack.models.UserModel;
import com.fintrack.FinTrack.service.UserService;

@RestController
@RequestMapping("employee")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllEmployee")
    public List<UserModel> getAllEmployee() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserModel getEmployeeById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
