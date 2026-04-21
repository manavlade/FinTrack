package com.fintrack.FinTrack.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintrack.FinTrack.DTO.AuthRequest;
import com.fintrack.FinTrack.DTO.AuthResponse;
import com.fintrack.FinTrack.utils.JWTUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponse login(@Validated @RequestBody AuthRequest authRequest) {

         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmployeeEmail(),
                        authRequest.getEmployeePassword()));

        String token = jwtUtil.generateToken(authRequest.getEmployeeEmail());

        return new AuthResponse(token);
    }
}
