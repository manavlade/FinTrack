package com.fintrack.FinTrack.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintrack.FinTrack.models.UploadJob;
import com.fintrack.FinTrack.models.UploadStatus;
import com.fintrack.FinTrack.repository.UploadJobRepository;

@RestController
@RequestMapping("/employees")

public class UploadHistoryController {

    private final UploadJobRepository uploadJobRepository;

    public UploadHistoryController(UploadJobRepository uploadJobRepository) {
        this.uploadJobRepository = uploadJobRepository;
    }

    @GetMapping("upload/analytics")
    public Page<UploadJob> getHistory(
            @RequestParam(required = false) UploadStatus status,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            Pageable pageable) {

        Long userId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (status != null && from != null && to != null) {
            return uploadJobRepository
                    .findByUploadedByIdAndStatusAndUploadedAtBetween(
                            userId, status, from, to, pageable);
        }

        if (from != null && to != null) {
            return uploadJobRepository
                    .findByUploadedByIdAndUploadedAtBetween(
                            userId, from, to, pageable);
        }

        if (status != null) {
            return uploadJobRepository
                    .findByUploadedByIdAndStatus(
                            userId, status, pageable);
        }

        return uploadJobRepository.findByUploadedById(userId, pageable);
    }
}
