package com.ironhack.bankingsystem.repository;

import com.ironhack.bankingsystem.model.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {
    public Optional<StudentChecking> findBySecretKey(Integer secretKey);
}
