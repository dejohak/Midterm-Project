package com.ironhack.bankingsystem.controller.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.AccountHolderDTO;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.controller.interfaces.IBankingSystemController;
import com.ironhack.bankingsystem.model.AccountHolder;
import com.ironhack.bankingsystem.service.impl.BakingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class BankingSystemController implements IBankingSystemController {

    @Autowired
    private BakingSystemService bakingSystemService;

    @PostMapping("/create/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        return bakingSystemService.createAccountHolder(accountHolderDTO);
    }

    @PostMapping("/create/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createChecking(@RequestBody CheckingDTO checkingDTO) {
        return bakingSystemService.validateAgeToCreateAccount(checkingDTO);
    }

    @GetMapping("/get/checking-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getCheckingBalance(@PathVariable @Valid Long id) {
        return bakingSystemService.getCheckingBalance(id);
    }

    @GetMapping("/get/student-checking-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getStudentCheckingBalance(@PathVariable @Valid Long id) {
        return bakingSystemService.getStudentCheckingBalance(id);
    }

    @GetMapping("/get/credit-card-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getCreditCardBalance(@PathVariable @Valid Long id) {
        return bakingSystemService.getCreditCardBalance(id);
    }

    @GetMapping("/get/savings-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getSavingsBalance(@PathVariable @Valid Long id) {
        return bakingSystemService.getSavingsBalance(id);
    }

    @PatchMapping("/credit/checking-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditCheckingBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bakingSystemService.updateCheckingBalance(id, quantity);
    }

    @PatchMapping("/credit/student-checking-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditStudentCheckingBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bakingSystemService.updateStudentCheckingBalance(id, quantity);
    }

    @PatchMapping("/credit/savings-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditSavingsBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bakingSystemService.updateSavingsBalance(id, quantity);
    }

    @PatchMapping("/credit/credit-card-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditCreditCardBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bakingSystemService.updateCreditCardBalance(id, quantity);
    }

    @PatchMapping("/debit/checking-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitCheckingBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bakingSystemService.updateCheckingBalance(id, quantity);
    }

    @PatchMapping("/debit/student-checking-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitStudentCheckingBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bakingSystemService.updateStudentCheckingBalance(id, quantity);
    }

    @PatchMapping("/debit/savings-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitSavingsBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bakingSystemService.updateSavingsBalance(id, quantity);
    }

    @PatchMapping("/debit/credit-card-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitCreditCardBalance(@PathVariable @Valid Long id, @RequestBody @Valid BigDecimal quantity) {
        bakingSystemService.updateCreditCardBalance(id, quantity);
    }
}
