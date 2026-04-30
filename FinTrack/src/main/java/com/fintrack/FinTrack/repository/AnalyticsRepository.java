package com.fintrack.FinTrack.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fintrack.FinTrack.models.UploadJob;

import org.springframework.data.repository.query.Param;

public interface AnalyticsRepository extends JpaRepository<UploadJob, Long> {

    @Query("""
                SELECT
                    COUNT(u),
                    SUM(CASE WHEN u.status = com.fintrack.FinTrack.models.UploadStatus.SUCCESS THEN 1 ELSE 0 END),
                    SUM(CASE WHEN u.status = com.fintrack.FinTrack.models.UploadStatus.FAILED THEN 1 ELSE 0 END),
                    SUM(CASE WHEN u.status = com.fintrack.FinTrack.models.UploadStatus.PARTIAL THEN 1 ELSE 0 END),
                    SUM(u.totalRows),
                    SUM(u.validRows),
                    SUM(u.invalidRows)
                FROM UploadJob u
                WHERE (CAST(:from AS LocalDateTime) IS NULL OR u.uploadedAt >= :from)
                AND (CAST(:to AS LocalDateTime) IS NULL OR u.uploadedAt <= :to)
            """)
    Object getSummary(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("""
                SELECT DATE(u.uploadedAt), COUNT(u)
                FROM UploadJob u
                WHERE (CAST(:from AS LocalDateTime) IS NULL OR u.uploadedAt >= :from)
                  AND (CAST(:to AS LocalDateTime) IS NULL OR u.uploadedAt <= :to)
                GROUP BY DATE(u.uploadedAt)
                ORDER BY DATE(u.uploadedAt)
            """)
    List<Object[]> getUploadTrend(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("""
                SELECT u.uploadedBy.employeeEmail, COUNT(u)
                FROM UploadJob u
                Where (CAST (:from AS LocalDateTime) IS NULL OR u.uploadedAt >= :from)
                  AND (CAST (:to AS LocalDateTime) IS NULL OR u.uploadedAt <= :to)
                GROUP BY u.uploadedBy.employeeEmail
                ORDER BY COUNT(u) DESC
            """)
    List<Object[]> getUserStats(@Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    @Query("""
                SELECT
                    (SUM(CASE WHEN u.status = com.fintrack.FinTrack.models.UploadStatus.SUCCESS THEN 1 ELSE 0 END) * 100.0 / COUNT(u))
                FROM UploadJob u
            """)
    Double getSuccessRate();
}
