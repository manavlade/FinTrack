package com.fintrack.FinTrack.parser;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import com.fintrack.FinTrack.config.EmployeeFieldConfig;

public class RowParser {

    public static class ParsedRow {

        public int rowNumber;
        public Map<String, String> stringValues = new LinkedHashMap<>();
        public Map<String, Object> numericValues = new LinkedHashMap<>();
        public boolean isBlank;

        public ParsedRow(int rowNumber) {
            this.rowNumber = rowNumber;
        }
    }

    public static ParsedRow parse(Row row,
            Map<String, Integer> columnMap,
            DataFormatter formatter) {

        ParsedRow result = new ParsedRow(row.getRowNum() + 1);
        boolean allBlank = true;

        for (EmployeeFieldConfig.FieldDef field : EmployeeFieldConfig.FIELDS) {

            String fieldName = field.employeeName();
            Integer colIndex = columnMap.get(fieldName);

            if (colIndex == null)
                continue;

            Cell cell = row.getCell(colIndex);
            String rawValue = formatter.formatCellValue(cell).trim();

            if (!rawValue.isBlank()) {
                allBlank = false;
            }

            // STRING fields
            if (field.type() == EmployeeFieldConfig.FieldType.STRING) {
                result.stringValues.put(fieldName, rawValue);
            }

            // NUMERIC fields
            else {
                try {
                    double value = Double.parseDouble(rawValue.replaceAll(",", ""));
                    result.numericValues.put(fieldName, value);
                    result.numericValues.put(fieldName + "_isNumeric", true);
                } catch (Exception e) {
                    result.numericValues.put(fieldName, 0.0);
                    result.numericValues.put(fieldName + "_isNumeric", false);
                }
            }
        }

        result.isBlank = allBlank;
        return result;
    }
}