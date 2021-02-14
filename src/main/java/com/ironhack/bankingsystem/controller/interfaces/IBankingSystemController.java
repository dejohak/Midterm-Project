package com.ironhack.bankingsystem.controller.interfaces;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.*;
import com.ironhack.bankingsystem.model.Account;
import com.ironhack.bankingsystem.model.AccountHolder;
import com.ironhack.bankingsystem.model.Checking;
import com.ironhack.bankingsystem.model.ThirdParty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


public interface IBankingSystemController {
    public List<Checking> getAccounts();
    public Object accessAccount(Integer secretKey);
    public Object accessAccountAdmin(Long id);
    public AccountHolder getAccountHolder(Long id);
    public Money getCheckingBalance(Integer secretKey);
    public Money getStudentCheckingBalance(Integer secretKey);
    public Money getCreditCardBalance(Long id);
    public Money getSavingsBalance(Integer secretKey);
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
