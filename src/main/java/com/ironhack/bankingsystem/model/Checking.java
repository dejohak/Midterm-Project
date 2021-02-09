package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
public class Checking extends Account {
    @Id
    private String checkingId;
    private String secretKey;
    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public Checking(Long id, BigDecimal balance, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, BigDecimal penaltyFee) {
        super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
    }

    public Checking(Long id, BigDecimal balance, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner,
                    BigDecimal penaltyFee, String secretKey, Status status) {
        super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
        setSecretKey(secretKey);
        this.minimumBalance = new BigDecimal(250);
        this.monthlyMaintenanceFee = new BigDecimal(12);
        setStatus(status);
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setCheckingBalance(BigDecimal balance) {
        if (balance.doubleValue() < getMinimumBalance().doubleValue()) {
            System.err.println("The balance for a checking account can not be less than the minimum balance: 250." +
                    "A penalty fee of 40 will be deducted from the current balance.");
            super.setBalance(balance.subtract(BigDecimal.valueOf(40)));
        } else {
            super.setBalance(balance);
        }
    }
}
