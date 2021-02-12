package com.ironhack.bankingsystem.controller.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.AccountDTO;
import com.ironhack.bankingsystem.controller.dto.AccountHolderDTO;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.controller.interfaces.IBankingSystemController;
import com.ironhack.bankingsystem.model.Account;
import com.ironhack.bankingsystem.model.AccountHolder;
import com.ironhack.bankingsystem.service.impl.BakingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class BankingSystemController implements IBankingSystemController {

    @Autowired
    private BakingSystemService bakingSystemService;

    @PostMapping("/create/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        return bakingSystemService.createAccountHolder(accountHolderDTO);
    }

    @PostMapping("/create/account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody @Valid AccountDTO accountDTO) {
        return bakingSystemService.createAccount(accountDTO);
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
}
