package com.ironhack.bankingsystem.controller.dto;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.model.AccountHolder;

public class CheckingDTO {
    private Long id;
    private Money balance;
    private AccountHolder accountHolder;

    public CheckingDTO(Long id, Money balance, AccountHolder accountHolder) {
        this.id = id;
        this.balance = balance;
        this.accountHolder = accountHolder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }
}
