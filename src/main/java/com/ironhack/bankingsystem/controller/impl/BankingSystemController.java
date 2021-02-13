package com.ironhack.bankingsystem.controller.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.AccountHolderDTO;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.controller.dto.ThirdPartyDTO;
import com.ironhack.bankingsystem.controller.interfaces.IBankingSystemController;
import com.ironhack.bankingsystem.model.AccountHolder;
import com.ironhack.bankingsystem.model.ThirdParty;
import com.ironhack.bankingsystem.service.impl.BankingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class BankingSystemController implements IBankingSystemController {

    @Autowired
    private BankingSystemService bankingSystemService;

    @GetMapping("/account-holder/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolder(@PathVariable Long id) {
        return bankingSystemService.findAccountHolder(id);
    }

    @PostMapping("/create/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        return bankingSystemService.createAccountHolder(accountHolderDTO);
    }
//  Admins must provide a name and a password to create the third party user. The username will be the third party's
//  name to lower case + "_tp", the password will be automatically encoded, and the hashed key will be set by encoding
//  the provided name.
    @PostMapping("/create/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        return bankingSystemService.createThirdParty(thirdPartyDTO.getName(), thirdPartyDTO.getPassword());
    }

    @PostMapping("/create/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createChecking(@RequestBody CheckingDTO checkingDTO) {
        return bankingSystemService.validateAgeToCreateAccount(checkingDTO);
    }

    @GetMapping("/get/checking-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getCheckingBalance(@PathVariable @Valid Long id) {
        return bankingSystemService.getCheckingBalance(id);
    }

    @GetMapping("/get/student-checking-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getStudentCheckingBalance(@PathVariable @Valid Long id) {
        return bankingSystemService.getStudentCheckingBalance(id);
    }

    @GetMapping("/get/credit-card-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getCreditCardBalance(@PathVariable @Valid Long id) {
        return bankingSystemService.getCreditCardBalance(id);
    }

    @GetMapping("/get/savings-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getSavingsBalance(@PathVariable @Valid Long id) {
        return bankingSystemService.getSavingsBalance(id);
    }

    @PatchMapping("/credit/checking-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditCheckingBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bankingSystemService.updateCheckingBalance(id, quantity);
    }

    @PatchMapping("/credit/student-checking-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditStudentCheckingBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bankingSystemService.updateStudentCheckingBalance(id, quantity);
    }

    @PatchMapping("/credit/savings-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditSavingsBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bankingSystemService.updateSavingsBalance(id, quantity);
    }

    @PatchMapping("/credit/credit-card-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditCreditCardBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bankingSystemService.updateCreditCardBalance(id, quantity);
    }

    @PatchMapping("/debit/checking-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitCheckingBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bankingSystemService.updateCheckingBalance(id, quantity);
    }

    @PatchMapping("/debit/student-checking-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitStudentCheckingBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bankingSystemService.updateStudentCheckingBalance(id, quantity);
    }

    @PatchMapping("/debit/savings-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitSavingsBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bankingSystemService.updateSavingsBalance(id, quantity);
    }

    @PatchMapping("/debit/credit-card-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitCreditCardBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bankingSystemService.updateCreditCardBalance(id, quantity);
    }
}
