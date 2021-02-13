package com.ironhack.bankingsystem.repository;

import com.ironhack.bankingsystem.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
    public Optional<Savings> findBySecretKey(Integer secretKey);
}
