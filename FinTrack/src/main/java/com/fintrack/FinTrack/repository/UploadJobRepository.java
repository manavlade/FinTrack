package com.fintrack.FinTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.fintrack.FinTrack.models.UploadJob;

public interface UploadJobRepository extends JpaRepository<UploadJob, Long> {

    List<UploadJob> findByUploadedByIdOrderByUploadedAtDesc(Long userId);
}