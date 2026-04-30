package com.fintrack.FinTrack.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fintrack.FinTrack.DTO.AnalyticsSummary;
import com.fintrack.FinTrack.DTO.TrendKey;
import com.fintrack.FinTrack.DTO.UploadTrendPoint;
import com.fintrack.FinTrack.DTO.UserUploadStats;
import com.fintrack.FinTrack.repository.AnalyticsRepository;

@Service
public class AnalyticsService {

    private final AnalyticsRepository repo;

    public AnalyticsService(AnalyticsRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "analytics:summary", key = "#from + '-' + #to")
    public AnalyticsSummary getSummary(LocalDateTime from, LocalDateTime to) {

        Object[] r = (Object[]) repo.getSummary(from, to);

        return new AnalyticsSummary(
                (Long) r[0],
                ((Number) r[1]).longValue(),
                ((Number) r[2]).longValue(),
                ((Number) r[3]).longValue(),
                ((Number) r[4]).longValue(),
                ((Number) r[5]).longValue(),
                ((Number) r[6]).longValue());
    }

    
    // @Cacheable(value = "analytics:trend", key = "#key")
    public List<UploadTrendPoint> getTrend(TrendKey key) {

        LocalDateTime from = key.from();
        LocalDateTime to = key.to();

        return repo.getUploadTrend(from, to).stream()
                .map(r -> new UploadTrendPoint(
                        toDateString(r[0]),
                        ((Number) r[1]).longValue()))
                .toList();
    }

    // @Cacheable(value = "analytics:user-stats", key = "#key")
    public List<UserUploadStats> getUserStats(TrendKey key) {

        LocalDateTime from = key.from();
        LocalDateTime to = key.to();

        return repo.getUserStats(from, to).stream()
                .map(r -> new UserUploadStats(
                        (String) r[0],
                        ((Number) r[1]).longValue()))
                .toList();
    }

    public double getSuccessRate() {
        return repo.getSuccessRate();
    }

    private String toDateString(Object value) {
        if (value instanceof LocalDate date) {
            return date.toString();
        }
        if (value instanceof LocalDateTime dateTime) {
            return dateTime.toLocalDate().toString();
        }
        if (value instanceof Date date) {
            return date.toLocalDate().toString();
        }
        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime().toLocalDate().toString();
        }
        return value.toString();
    }
}
