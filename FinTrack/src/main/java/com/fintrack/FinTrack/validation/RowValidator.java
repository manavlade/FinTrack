package com.fintrack.FinTrack.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fintrack.FinTrack.config.EmployeeFieldConfig;
import com.fintrack.FinTrack.config.EmployeeFieldConfig.FieldDef;
import com.fintrack.FinTrack.config.EmployeeFieldConfig.FieldType;

public class RowValidator {

    public static List<String> validate(int rowNumber,
            Map<String, String> stringValues,
            Map<String, Object> numericValues) {

        List<String> errors = new ArrayList<>();

        for (FieldDef field : EmployeeFieldConfig.FIELDS) {

            String fieldName = field.employeeName();

            // STRING VALIDATION
            if (field.type() == FieldType.STRING) {

                String value = stringValues.getOrDefault(fieldName, "");

                // required check
                if (field.required() && value.isBlank()) {
                    errors.add("Row " + rowNumber + ": " + fieldName + " is required");
                    continue;
                }

          

                // regex validation
                if (field.regexPattern() != null && !value.isBlank()) {
                    if (!value.matches(field.regexPattern())) {
                        errors.add("Row " + rowNumber + ": " + field.errorMessage());
                    }
                }
            }

            // NUMERIC VALIDATION
            else {

                boolean isNumeric = Boolean.TRUE.equals(
                        numericValues.getOrDefault(fieldName + "_isNumeric", false));

                if (!isNumeric) {
                    errors.add("Row " + rowNumber + ": " + fieldName + " must be a valid number");
                    continue;
                }

                double value = (double) numericValues.getOrDefault(fieldName, 0.0);

                if (field.minVal() != null && value < field.minVal()) {
                    errors.add("Row " + rowNumber + ": " + fieldName +
                            " must be >= " + field.minVal());
                }

                if (field.maxVal() != null && value > field.maxVal()) {
                    errors.add("Row " + rowNumber + ": " + fieldName +
                            " must be <= " + field.maxVal());
                }
            }
        }

        return errors;
    }
}