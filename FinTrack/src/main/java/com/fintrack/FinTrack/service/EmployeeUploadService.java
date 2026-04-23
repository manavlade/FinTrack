package com.fintrack.FinTrack.service;

import java.io.InputStream;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fintrack.FinTrack.models.EmployeeUpload;
import com.fintrack.FinTrack.repository.EmployeeeUploadRepository;

@Service
public class EmployeeUploadService {

    private final EmployeeeUploadRepository employeeUploadRepository;

    public EmployeeUploadService(EmployeeeUploadRepository employeeeUploadRepository) {
        this.employeeUploadRepository = employeeeUploadRepository;
    }

    public Map<String, Object> upload(MultipartFile file) {

        try (InputStream is = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(is)) {

            ExcelProcessor.ProcessResult result = ExcelProcessor.process(file, workbook);

            // SAVE VALID DATA (INSERT/UPDATE LOGIC)
            for (EmployeeUpload emp : result.validEmployees) {
                employeeUploadRepository.save(emp);
            }

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
