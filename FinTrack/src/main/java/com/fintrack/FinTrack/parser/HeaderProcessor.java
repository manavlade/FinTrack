package com.fintrack.FinTrack.parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintrack.FinTrack.config.EmployeeFieldConfig;


public class HeaderProcessor {

    private static final Logger logger = LoggerFactory.getLogger(HeaderProcessor.class);
    private static final int MAX_TYPOS_ALLOWED = 2;

    public static class HeaderResult {
        public Map<String, Integer> columnMap;
        public List<String> warnings;

        public HeaderResult(Map<String, Integer> columnMap, List<String> warnings) {
            this.columnMap = columnMap;
            this.warnings = warnings;
        }
    }

    public static HeaderResult process(Row headerRow, DataFormatter formatter) {

        Map<String, Integer> columnMap = new LinkedHashMap<>();
        List<String> warnings = new java.util.ArrayList<>();

        Map<String, Boolean> found = new LinkedHashMap<>();
        for (String field : EmployeeFieldConfig.getFieldName()) {
            found.put(field, false);
        }

        for (Cell cell : headerRow) {

            int index = cell.getColumnIndex();
            String header = formatter.formatCellValue(cell).trim();

            if (header.isBlank()) continue;

            String matchedField = findBestMatch(header);

            if (matchedField != null) {

                columnMap.put(matchedField, index);
                found.put(matchedField, true);

                if (!header.equalsIgnoreCase(matchedField)) {
                    warnings.add("Column '" + header + "' interpreted as '" + matchedField + "'");
                    logger.warn("Header mismatch → '{}' mapped to '{}'", header, matchedField);
                }
            } else {
                logger.warn("Ignoring unknown column: {}", header);
            }
        }

        // Check missing required fields
        for (Map.Entry<String, Boolean> entry : found.entrySet()) {
            if (!entry.getValue()) {
                warnings.add("Missing required column: " + entry.getKey());
            }
        }

        return new HeaderResult(columnMap, warnings);
    }

    // Simple Levenshtein-based fuzzy match
    private static String findBestMatch(String input) {

        String bestMatch = null;
        int bestDistance = Integer.MAX_VALUE;

        for (String field : EmployeeFieldConfig.getFieldName()) {

            int distance = levenshtein(input.toLowerCase(), field.toLowerCase());

            if (distance < bestDistance) {
                bestDistance = distance;
                bestMatch = field;
            }
        }

        return bestDistance <= MAX_TYPOS_ALLOWED ? bestMatch : null;
    }

    private static int levenshtein(String a, String b) {

        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])
                    );
                }
            }
        }

        return dp[a.length()][b.length()];
    }
}