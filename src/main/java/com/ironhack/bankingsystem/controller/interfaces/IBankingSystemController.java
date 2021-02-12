package com.ironhack.bankingsystem.controller.interfaces;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.AccountDTO;
import com.ironhack.bankingsystem.controller.dto.AccountHolderDTO;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.model.Account;
import com.ironhack.bankingsystem.model.AccountHolder;


public interface IBankingSystemController {
    public Money getCheckingBalance(Long id);
    public Money getStudentCheckingBalance(Long id);
    public Money getCreditCardBalance(Long id);
    public Object createChecking(CheckingDTO checkingDTO);
    public Account createAccount(AccountDTO accountDTO);
    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
}
