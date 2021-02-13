package com.ironhack.bankingsystem.controller.interfaces;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.AccountHolderDTO;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.controller.dto.ThirdPartyDTO;
import com.ironhack.bankingsystem.model.AccountHolder;
import com.ironhack.bankingsystem.model.ThirdParty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;


public interface IBankingSystemController {
    public AccountHolder getAccountHolder(Long id);
    public Money getCheckingBalance(Long id);
    public Money getStudentCheckingBalance(Long id);
    public Money getCreditCardBalance(Long id);
    public Money getSavingsBalance(Long id);
    public Object createChecking(CheckingDTO checkingDTO);
    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
    public ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO);
    public void creditCheckingBalance(Long id, BigDecimal quantity);
    public void creditStudentCheckingBalance(Long id, BigDecimal quantity);
    public void creditSavingsBalance(Long id, BigDecimal quantity);
    public void creditCreditCardBalance(Long id, BigDecimal quantity);
    public void debitCheckingBalance(Long id, BigDecimal quantity);
    public void debitStudentCheckingBalance(Long id, BigDecimal quantity);
    public void debitSavingsBalance(Long id, BigDecimal quantity);
    public void debitCreditCardBalance(Long id, BigDecimal quantity);
}
