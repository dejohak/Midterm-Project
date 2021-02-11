package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Checking {
    @Id
    private String checkingId;
    @Embedded
    private Money balance;
    private String secretKey;
    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    private Account account;


    public Checking() {
        this.minimumBalance = new BigDecimal(250);
        this.monthlyMaintenanceFee = new BigDecimal(12);
    }

    public Checking(String checkingId, Money balance, String secretKey, Status status) {
        setBalance(balance);
        setCheckingId(checkingId);
        this.minimumBalance = new BigDecimal(250);
        this.monthlyMaintenanceFee = new BigDecimal(12);
        setSecretKey(secretKey);
        setStatus(status);
    }

    public Checking(String checkingId, Money balance, String secretKey, Status status, Account account) {
        setBalance(balance);
        setCheckingId(checkingId);
        this.minimumBalance = new BigDecimal(250);
        this.monthlyMaintenanceFee = new BigDecimal(12);
        setSecretKey(secretKey);
        setStatus(status);
        setAccount(account);
    }

    public String getCheckingId() {
        return checkingId;
    }

    public void setCheckingId(String checkingId) {
        this.checkingId = checkingId;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCheckingBalance(Money balance) {
        if (balance.getAmount().doubleValue() < minimumBalance.doubleValue()) {
            System.err.println("The balance for a checking account can not be less than the minimum balance: 250." +
                    "A penalty fee of 40 will be deducted from the current balance.");
            balance.decreaseAmount(getAccount().getPenaltyFee());
        }
        this.balance = balance;
    }
}
