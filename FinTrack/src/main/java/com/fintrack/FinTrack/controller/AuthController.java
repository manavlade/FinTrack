package com.fintrack.FinTrack.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintrack.FinTrack.DTO.LoginRequest;
import com.fintrack.FinTrack.DTO.AuthResponse;
import com.fintrack.FinTrack.DTO.SignUpRequest;
import com.fintrack.FinTrack.DTO.SignUpResponse;
import com.fintrack.FinTrack.models.UserModel;
import com.fintrack.FinTrack.service.UserService;
import com.fintrack.FinTrack.utils.JWTUtil;

import jakarta.validation.Valid;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponse login(@Validated @RequestBody LoginRequest authRequest) {

         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmployeeEmail(),
                        authRequest.getEmployeePassword()));

        String token = jwtUtil.generateToken(authRequest.getEmployeeEmail());

        return new AuthResponse(token);
    }   
    
     @PostMapping("/signup")
    public SignUpResponse signup(@Valid @RequestBody SignUpRequest request) {

        UserModel user = new UserModel();
        user.setEmployeeEmail(request.getEmployeeEmail());
        user.setEmployeePassword(request.getEmployeePassword());
        user.setEmployeeRole(request.getEmployeeRole());

        UserModel savedUser = userService.createUser(user);

        SignUpResponse response = new SignUpResponse();
        response.setId(savedUser.getId());
        response.setEmployeeEmail(savedUser.getEmployeeEmail());
        response.setEmployeeRole(savedUser.getEmployeeRole());

        return response;
    }
}
