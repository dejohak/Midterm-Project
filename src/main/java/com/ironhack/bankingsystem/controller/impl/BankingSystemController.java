package com.ironhack.bankingsystem.controller.impl;

import com.ironhack.bankingsystem.controller.interfaces.IBankingSystemController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class BankingSystemController implements IBankingSystemController {

    /*@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getCheckingBalance() {
        return ;
    }*/
}
