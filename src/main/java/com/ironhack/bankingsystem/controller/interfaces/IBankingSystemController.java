package com.ironhack.bankingsystem.controller.interfaces;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.*;
import com.ironhack.bankingsystem.model.Account;
import com.ironhack.bankingsystem.model.AccountHolder;
import com.ironhack.bankingsystem.model.ThirdParty;

import java.util.List;


public interface IBankingSystemController {
    public Object accessAccount(Integer secretKey);
    public Object accessAccountAdmin(Long id);
    public AccountHolder getAccountHolder(Long id);
    public Money getAccountBalance(Integer secretKey);
    public Money getCreditCardBalance(Long id);
    public void transferMoney(Long id, Long targetId, QuantityDTO quantityDTO);
    public List<Account> accounts();
    public void creditAccountBalance(Long id, QuantityDTO quantity);
    public void debitAccountBalance(Long id, QuantityDTO quantity);
    public void creditAccountTP(Integer hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);
    public void debitAccountTP(Integer hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);
    public Object createChecking(CheckingDTO checkingDTO);
    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
    public ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO);
}
