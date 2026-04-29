package com.fintrack.FinTrack.DTO;

public record AnalyticsSummary(
        long totalUploads,
        long success,
        long failed,
        long partial,
        long totalRows,
        long validRows,
        long invalidRows) {
}