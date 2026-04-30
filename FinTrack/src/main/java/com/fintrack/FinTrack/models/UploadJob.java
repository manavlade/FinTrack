package com.fintrack.FinTrack.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "upload_job",
    indexes = {
        @Index(name = "idx_upload_job_status", columnList = "status"),
        @Index(name = "idx_upload_job_uploaded_at", columnList = "uploadedAt"),
        @Index(name = "idx_upload_job_user_id", columnList = "user_id"),
        @Index(name = "idx_status_uploaded_at", columnList = "status, uploadedAt")
    }
)
public class UploadJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private LocalDateTime uploadedAt;

    @Enumerated(EnumType.STRING)
    private UploadStatus status; 

    private int totalRows;
    private int validRows;
    private int invalidRows;

    @Column(columnDefinition = "TEXT")
    private String processingNotes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel uploadedBy;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getInvalidRows() {
        return invalidRows;
    }

    public void setInvalidRows(int invalidRows) {
        this.invalidRows = invalidRows;
    }

    public UploadStatus getStatus() {
        return status;
    }

    public void setStatus(UploadStatus status) {
        this.status = status;
    }

    public String getProcessingNotes() {
        return processingNotes;
    }

    public void setProcessingNotes(String processingNotes) {
        this.processingNotes = processingNotes;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public int getValidRows() {
        return validRows;
    }

    public void setValidRows(int validRows) {
        this.validRows = validRows;
    }

    public UserModel getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserModel uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

}