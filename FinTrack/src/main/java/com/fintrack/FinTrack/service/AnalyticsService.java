package com.fintrack.FinTrack.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fintrack.FinTrack.DTO.AnalyticsSummary;
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

    @Cacheable(value = "analytics:trend", key = "#from + '-' + #to")
    public List<Map<String, Object>> getTrend(LocalDateTime from, LocalDateTime to) {
        return repo.getUploadTrend(from, to).stream()
                .map(r -> Map.of(
                        "date", r[0],
                        "count", r[1]))
                .toList();
    }

    @Cacheable(value = "analytics:user-stats", key = "#from + '-' + #to")
    public List<Map<String, Object>> getUserStats(LocalDateTime from, LocalDateTime to) {
        return repo.getUserStats(from, to).stream()
                .map(r -> Map.of(
                        "email", r[0],
                        "uploads", r[1]))
                .toList();
    }

    public double getSuccessRate() {
        return repo.getSuccessRate();
    }
}
