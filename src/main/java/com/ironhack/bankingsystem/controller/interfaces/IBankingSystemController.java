package com.ironhack.bankingsystem.controller.interfaces;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.AccountHolderDTO;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.controller.dto.QuantityDTO;
import com.ironhack.bankingsystem.controller.dto.ThirdPartyDTO;
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
    public AccountHolder getAccountHolder(Long id);
    public Money getCheckingBalance(Integer secretKey);
    public Money getStudentCheckingBalance(Integer secretKey);
    public Money getCreditCardBalance(Long id);
    public Money getSavingsBalance(Integer secretKey);
    public void transferMoney(Long id, Long targetId, BigDecimal amount);
    public List<Account> accounts();
    public void creditAccountBalance(Long id, QuantityDTO quantity);
    public void debitAccountBalance(Long id, QuantityDTO quantity);
    /*public Object createChecking(CheckingDTO checkingDTO);
    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
    public ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO);
   /* public void creditCheckingBalance(Long id, BigDecimal quantity);
    public void creditStudentCheckingBalance(Long id, BigDecimal quantity);
    public void creditSavingsBalance(Long id, BigDecimal quantity);
    public void creditCreditCardBalance(Long id, BigDecimal quantity);
    public void debitCheckingBalance(Long id, BigDecimal quantity);
    public void debitStudentCheckingBalance(Long id, BigDecimal quantity);
    public void debitSavingsBalance(Long id, BigDecimal quantity);
    public void debitCreditCardBalance(Long id, BigDecimal quantity);*/
}
