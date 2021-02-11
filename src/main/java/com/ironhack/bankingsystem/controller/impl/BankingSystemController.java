package com.ironhack.bankingsystem.controller.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.interfaces.IBankingSystemController;
import com.ironhack.bankingsystem.repository.CheckingRepository;
import com.ironhack.bankingsystem.service.impl.BakingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class BankingSystemController implements IBankingSystemController {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private BakingSystemService bakingSystemService;

    @GetMapping("/get/checking-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getCheckingBalance(@PathVariable @Valid Long id) {
        Money balance = bakingSystemService.getCheckingBalance(id);
        return balance;
    }
}
