package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.enums.Status;

import javax.persistence.*;

@Entity
public class StudentChecking {
    @Id
    private Long studentCheckingId;
    private Integer secretKey;
    private Money balance;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    private Account account;

    public StudentChecking() {
    }

    public StudentChecking(Long studentCheckingId, Integer secretKey, Status status) {
        this.studentCheckingId = studentCheckingId;
        this.secretKey = secretKey;
        this.status = status;
    }

    public StudentChecking(Money balance, Long studentCheckingId, Integer secretKey, Status status) {
        setStudentCheckingId(studentCheckingId);
        setBalance(balance);
        setSecretKey(secretKey);
        setStatus(status);
    }

    public Long getStudentCheckingId() {
        return studentCheckingId;
    }

    public void setStudentCheckingId(Long studentCheckingId) {
        this.studentCheckingId = studentCheckingId;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Integer getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Integer secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
