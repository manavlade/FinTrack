package com.fintrack.FinTrack.service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fintrack.FinTrack.models.EmployeeUpload;
import com.fintrack.FinTrack.models.UploadJob;
import com.fintrack.FinTrack.models.UserModel;
import com.fintrack.FinTrack.repository.EmployeeeUploadRepository;
import com.fintrack.FinTrack.repository.UploadJobRepository;
import com.fintrack.FinTrack.repository.UserRepository;

@Service
public class EmployeeUploadService {

    private final EmployeeeUploadRepository employeeUploadRepository;
    private final UserRepository userRepository;
    private final UploadJobRepository uploadJobRepository;

    public EmployeeUploadService(EmployeeeUploadRepository employeeeUploadRepository, UserRepository userRepository, UploadJobRepository uploadJobRepository) {
        this.employeeUploadRepository = employeeeUploadRepository;
        this.userRepository = userRepository;
        this.uploadJobRepository = uploadJobRepository;
    }

    public Map<String, Object> upload(MultipartFile file) {

        try (InputStream is = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(is)) {

            ExcelProcessor.ProcessResult result = ExcelProcessor.process(file, workbook);

            for (EmployeeUpload emp : result.validEmployees) {
                employeeUploadRepository.save(emp);
            }

            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            UserModel user = userRepository.findByEmployeeEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UploadJob job = new UploadJob();

            job.setFilename(file.getOriginalFilename());
            job.setFilename(file.getOriginalFilename());
            job.setUploadedAt(LocalDateTime.now());
            job.setUploadedBy(user);
            job.setTotalRows(result.validEmployees.size() + result.errors.size());
            job.setValidRows(result.validEmployees.size());
            job.setInvalidRows(result.errors.size());
            job.setStatus(result.errors.isEmpty() ? "SUCCESS" : "PARTIAL");

            uploadJobRepository.save(job);

            return Map.of(
                    "message", "Upload completed",
                    "successCount", result.validEmployees.size(),
                    "errorCount", result.errors.size(),
                    "errors", result.errors,
                    "warnings", result.warnings);

        } catch (Exception e) {
            throw new RuntimeException("Failed to process Excel file: " + e.getMessage(), e);
        }
    }
}
