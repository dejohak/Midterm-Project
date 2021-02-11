package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.enums.Status;

import javax.persistence.*;

@Entity
public class StudentChecking {
    @Id
    private String studentCheckingId;
    private String secretKey;
    private Money balance;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    private Account account;

    public StudentChecking() {
    }

    public StudentChecking(Money balance, String studentCheckingId, String secretKey, Status status) {
        setStudentCheckingId(studentCheckingId);
        setBalance(balance);
        setSecretKey(secretKey);
        setStatus(status);
    }

    public String getStudentCheckingId() {
        return studentCheckingId;
    }

    public void setStudentCheckingId(String studentCheckingId) {
        this.studentCheckingId = studentCheckingId;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
