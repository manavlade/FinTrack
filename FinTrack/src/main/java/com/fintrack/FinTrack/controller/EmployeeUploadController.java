package com.fintrack.FinTrack.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fintrack.FinTrack.service.EmployeeUploadService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeUploadController {

    private final EmployeeUploadService service;

    public EmployeeUploadController(EmployeeUploadService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {

        Map<String, Object> response = service.upload(file);

        return ResponseEntity.ok(response);
    }
}