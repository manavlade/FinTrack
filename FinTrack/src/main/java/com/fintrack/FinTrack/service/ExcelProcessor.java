package com.fintrack.FinTrack.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import com.fintrack.FinTrack.models.EmployeeUpload;
import com.fintrack.FinTrack.parser.HeaderProcessor;
import com.fintrack.FinTrack.parser.RowParser;
import com.fintrack.FinTrack.parser.RowParser.ParsedRow;
import com.fintrack.FinTrack.validation.FileValidator;
import com.fintrack.FinTrack.validation.RowValidator;


public class ExcelProcessor {

    public static class ProcessResult {
        public List<EmployeeUpload> validEmployees = new ArrayList<>();
        public List<String> errors = new ArrayList<>();
        public List<String> warnings = new ArrayList<>();
    }

    public static ProcessResult process(MultipartFile file, Workbook workbook) {

        // STEP 1: Validate file
        FileValidator.validate(file);

        ProcessResult result = new ProcessResult();
        DataFormatter formatter = new DataFormatter();

        Sheet sheet = workbook.getSheetAt(0);

        // STEP 2: Header processing
        HeaderProcessor.HeaderResult headerResult =
                HeaderProcessor.process(sheet.getRow(0), formatter);

        result.warnings.addAll(headerResult.warnings);

        Map<String, Integer> columnMap = headerResult.columnMap;

        // STEP 3: Row processing loop
        for (Row row : sheet) {

            if (row.getRowNum() == 0) continue; // skip header

            ParsedRow parsedRow = RowParser.parse(row, columnMap, formatter);

            if (parsedRow.isBlank) continue;

            // STEP 4: Validate row
            List<String> rowErrors = RowValidator.validate(
                    parsedRow.rowNumber,
                    parsedRow.stringValues,
                    parsedRow.numericValues
            );

            // STEP 5: Separate valid / invalid
            if (!rowErrors.isEmpty()) {
                result.errors.addAll(rowErrors);
                continue;
            }

            // STEP 6: Convert to entity
            EmployeeUpload emp = new EmployeeUpload();
            emp.setName(parsedRow.stringValues.get("name"));
            emp.setEmail(parsedRow.stringValues.get("email"));
            emp.setPassword(parsedRow.stringValues.get("password"));

            emp.setAge((int) (double) parsedRow.numericValues.getOrDefault("age", 0.0));
            emp.setSalary((double) parsedRow.numericValues.getOrDefault("salary", 0.0));

            result.validEmployees.add(emp);
        }

        return result;
    }
}