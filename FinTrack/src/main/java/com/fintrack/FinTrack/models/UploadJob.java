package com.fintrack.FinTrack.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UploadJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private LocalDateTime uploadedAt;

    private String status; 

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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