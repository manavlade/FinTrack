package com.fintrack.FinTrack.service;

import java.io.InputStream;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fintrack.FinTrack.models.EmployeeUpload;
import com.fintrack.FinTrack.models.UserModel;
import com.fintrack.FinTrack.repository.EmployeeeUploadRepository;
import com.fintrack.FinTrack.repository.UserRepository;

@Service
public class EmployeeUploadService {

    private final EmployeeeUploadRepository employeeUploadRepository;
    private final UserRepository userRepository;

    public EmployeeUploadService(EmployeeeUploadRepository employeeeUploadRepository, UserRepository userRepository) {
        this.employeeUploadRepository = employeeeUploadRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> upload(MultipartFile file) {

        try (InputStream is = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(is)) {

            String email = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
            UserModel user = userRepository.findByEmployeeEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ExcelProcessor.ProcessResult result = ExcelProcessor.process(file, workbook);

            for (EmployeeUpload emp : result.validEmployees) {
                if (employeeUploadRepository.existsByEmail(emp.getEmail())) {
                    result.errors.add("Email already exists: " + emp.getEmail());
                    continue; // skip saving
                }

                emp.setUploadedBy(user);
                employeeUploadRepository.save(emp);
            }

            return Map.of(
                    "message", "Upload completed",
                    "successCount", result.validEmployees.size(),
                    "errorCount", result.errors.size(),
                    "errors", result.errors,
                    "warnings", result.warnings);

        } catch (Exception e) {
            throw new RuntimeException("Failed to process Excel file {}" + e.getMessage(), e);
        }
    }

}
