package com.fintrack.FinTrack.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintrack.FinTrack.DTO.AnalyticsSummary;
import com.fintrack.FinTrack.DTO.TrendKey;
import com.fintrack.FinTrack.DTO.UploadTrendPoint;
import com.fintrack.FinTrack.DTO.UserUploadStats;
import com.fintrack.FinTrack.service.AnalyticsService;

@RestController
@RequestMapping("/admin/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public AnalyticsSummary getSummary(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {
        return service.getSummary(from, to);
    }

    @GetMapping("/upload-trend")
     public List<UploadTrendPoint> getTrend(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {
        return service.getTrend(new TrendKey(from, to));
    }
    

    @GetMapping("/user-stats")
     public List<UserUploadStats> getUserStats(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {
        return service.getUserStats(new TrendKey(from, to));
    }

    @GetMapping("/success-rate")
    public double getSuccessRate() {
        return service.getSuccessRate();
    }
}
