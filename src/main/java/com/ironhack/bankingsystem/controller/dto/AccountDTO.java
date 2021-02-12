package com.ironhack.bankingsystem.controller.dto;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.model.AccountHolder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;


public class AccountDTO {
    @PositiveOrZero
    private Money balance;
    @NotEmpty
    private AccountHolder accountHolder;

    public AccountDTO(Money balance, AccountHolder accountHolder) {
        this.balance = balance;
        this.accountHolder = accountHolder;
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
