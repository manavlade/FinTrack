package com.fintrack.FinTrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintrack.FinTrack.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmployeeEmail(String employeeEmail);

    boolean existsByEmployeeEmail(String employeeEmail);
}
