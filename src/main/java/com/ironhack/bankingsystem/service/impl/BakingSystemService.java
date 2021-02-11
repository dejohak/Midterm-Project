package com.ironhack.bankingsystem.service.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.interfaces.IBankingSystemController;
import com.ironhack.bankingsystem.model.Checking;
import com.ironhack.bankingsystem.repository.CheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BakingSystemService implements IBankingSystemController {
    @Autowired
    private CheckingRepository checkingRepository;

    public Money getCheckingBalance(Long id) {
        Optional<Checking> checking = checkingRepository.findById(id.toString());
        if (checking.isPresent()) {
            return checking.get().getBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found.");
    }
}
