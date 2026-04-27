package com.fintrack.FinTrack.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fintrack.FinTrack.models.UploadJob;
import com.fintrack.FinTrack.models.UserModel;
import com.fintrack.FinTrack.repository.UploadJobRepository;
import com.fintrack.FinTrack.repository.UserRepository;
import com.fintrack.FinTrack.service.EmployeeUploadService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeUploadController {

    private final EmployeeUploadService service;
    private final UserRepository userRepository;
    private final UploadJobRepository uploadJobRepository;

    public EmployeeUploadController(EmployeeUploadService service, UserRepository userRepository,
            UploadJobRepository uploadJobRepository) {
        this.service = service;
        this.userRepository = userRepository;
        this.uploadJobRepository = uploadJobRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {

        Map<String, Object> response = service.upload(file);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/upload/history")
    public List<UploadJob> getUploadHistory() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserModel user = userRepository.findByEmployeeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return uploadJobRepository
                .findByUploadedByIdOrderByUploadedAtDesc(user.getId());
    }

}