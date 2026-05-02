package com.fintrack.FinTrack.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fintrack.FinTrack.models.UploadJob;
import com.fintrack.FinTrack.repository.EmployeeeUploadRepository;
import com.fintrack.FinTrack.repository.UploadJobRepository;
import com.fintrack.FinTrack.repository.UserRepository;
import com.fintrack.FinTrack.service.EmployeeUploadService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/employees")
public class EmployeeUploadController {

    private final EmployeeUploadService service;
    private final UserRepository userRepository;
    private final UploadJobRepository uploadJobRepository;
    private final EmployeeeUploadRepository employeeeUploadRepository;

    public EmployeeUploadController(EmployeeUploadService service, UserRepository userRepository,
            UploadJobRepository uploadJobRepository, EmployeeeUploadRepository employeeeUploadRepository) {
        this.service = service;
        this.userRepository = userRepository;
        this.uploadJobRepository = uploadJobRepository;
        this.employeeeUploadRepository = employeeeUploadRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {

        Map<String, Object> response = service.upload(file);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/upload/history")
    public List<UploadJob> getUploadHistory() {

        Long userId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return uploadJobRepository
                .findByUploadedByIdOrderByUploadedAtDesc(userId);
    }

}