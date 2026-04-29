package com.fintrack.FinTrack.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fintrack.FinTrack.models.UploadJob;
import com.fintrack.FinTrack.models.UploadStatus;

public interface UploadJobRepository extends JpaRepository<UploadJob, Long> {

    List<UploadJob> findByUploadedByIdOrderByUploadedAtDesc(Long userId);

    Page<UploadJob> findAll(Pageable pageable);

    Page<UploadJob> findByStatus(UploadStatus status, Pageable pageable);

    Page<UploadJob> findByUploadedAtBetween(
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable);

    Page<UploadJob> findByStatusAndUploadedAtBetween(
            UploadStatus status,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
        );

    
}