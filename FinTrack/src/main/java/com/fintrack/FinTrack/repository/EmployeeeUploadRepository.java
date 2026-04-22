package com.fintrack.FinTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fintrack.FinTrack.models.EmployeeUpload;

@Repository
public interface EmployeeeUploadRepository extends JpaRepository<EmployeeUpload, Long> {  


} 
