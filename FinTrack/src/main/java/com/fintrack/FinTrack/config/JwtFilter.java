package com.fintrack.FinTrack.config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fintrack.FinTrack.service.CustomUserDetailsService;
import com.fintrack.FinTrack.utils.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jsJwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtFilter(JWTUtil jsJwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jsJwtUtil = jsJwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        


    }
}
