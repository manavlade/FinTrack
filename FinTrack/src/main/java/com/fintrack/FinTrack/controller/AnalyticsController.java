package com.fintrack.FinTrack.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintrack.FinTrack.DTO.AnalyticsSummary;
import com.fintrack.FinTrack.DTO.TrendKey;
import com.fintrack.FinTrack.DTO.UploadTrendPoint;
import com.fintrack.FinTrack.DTO.UserUploadStats;
import com.fintrack.FinTrack.models.UploadJob;
import com.fintrack.FinTrack.models.UploadStatus;
import com.fintrack.FinTrack.repository.UploadJobRepository;
import com.fintrack.FinTrack.service.AnalyticsService;

@RestController
@RequestMapping("/admin/analytics")
public class AnalyticsController {

    private final AnalyticsService service;
    private final UploadJobRepository uploadJobRepository;

    public AnalyticsController(AnalyticsService service,
            UploadJobRepository uploadJobRepository) {
        this.service = service;
        this.uploadJobRepository = uploadJobRepository;
    }

    @GetMapping("/summary")
    public AnalyticsSummary getSummary(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to) {
        return service.getSummary(from, to);
    }

    @GetMapping("/upload-trend")
    public List<UploadTrendPoint> getTrend(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to) {
        return service.getTrend(new TrendKey(from, to));
    }

    @GetMapping("/user-stats")
    public List<UserUploadStats> getUserStats(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to) {
        return service.getUserStats(new TrendKey(from, to));
    }

    @GetMapping("/success-rate")
    public double getSuccessRate() {
        return service.getSuccessRate();
    }

    @GetMapping("/upload/history")
    public List<UploadJob> getUploadHistory() {
        return uploadJobRepository.findAllByOrderByUploadedAtDesc();
    }

    @GetMapping("upload/analytics")
    public Page<UploadJob> getHistory(
            @RequestParam(required = false) UploadStatus status,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            Pageable pageable) {

        if (status != null && from != null && to != null) {
            return uploadJobRepository.findByStatusAndUploadedAtBetween(
                    status, from, to, pageable);
        }

        if (from != null && to != null) {
            return uploadJobRepository.findByUploadedAtBetween(
                    from, to, pageable);
        }

        if (status != null) {
            return uploadJobRepository.findByStatus(status, pageable);
        }

        return uploadJobRepository.findAll(pageable);
    }
}
