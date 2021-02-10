package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Savings {
    @Id
    private String savingsId;
    private String secretKey;
    private BigDecimal balance;
    private BigDecimal minimumBalance;
    private Double interestRate;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    private LocalDateTime ldt;

    @ManyToOne
    private Account account;


    public Savings() {
        this.interestRate = 0.0025;
        this.minimumBalance = new BigDecimal(1000);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.ldt = ts.toLocalDateTime();
    }

    public Savings(String savingsId, BigDecimal balance, String secretKey, BigDecimal minimumBalance, Double interestRate,
                   Status status) {
        setBalance(balance);
        setSavingsId(savingsId);
        setSecretKey(secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
        setStatus(status);
    }

    public String getSavingsId() {
        return savingsId;
    }

    public void setSavingsId(String savingsId) {
        this.savingsId = savingsId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        if (interestRate <= 0.5) {
            this.interestRate = interestRate;
        } else {
            this.interestRate = 0.0025;
            System.err.println("Interest rate can't be greater than 0.5. It will be set to its default value.");
        }
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        if ( minimumBalance.doubleValue() >= 100 && minimumBalance.doubleValue() <= 1000) {
            this.minimumBalance = minimumBalance;
        } else {
            this.minimumBalance = new BigDecimal(1000);
            System.err.println("Minimum balance may be less than 1000 but no lower than 100. It will be set to its " +
                    "default value.");
        }

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public BigDecimal getBalance() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        LocalDateTime newLdt = ts.toLocalDateTime();
        if (newLdt.getYear() > getLdt().getYear()) {
            if (newLdt.getDayOfYear() >= getLdt().getDayOfYear()) {
                setBalance(getBalance().multiply(new BigDecimal(getInterestRate()+1)));
                setLdt(newLdt);
                return balance;
            }
        }
        return balance;
    }


}
