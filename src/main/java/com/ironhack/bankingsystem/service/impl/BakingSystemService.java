package com.ironhack.bankingsystem.service.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.interfaces.IBankingSystemController;
import com.ironhack.bankingsystem.model.Checking;
import com.ironhack.bankingsystem.model.CreditCard;
import com.ironhack.bankingsystem.model.StudentChecking;
import com.ironhack.bankingsystem.repository.CheckingRepository;
import com.ironhack.bankingsystem.repository.CreditCardRepository;
import com.ironhack.bankingsystem.repository.StudentCheckingRepository;
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
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;

    public Money getCheckingBalance(Long id) {
        Optional<Checking> checking = checkingRepository.findById(id);
        if (checking.isPresent()) {
            return checking.get().getBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found.");
    }

    public Money getStCheckingBalance(Long id) {
        Optional<StudentChecking> stChecking = studentCheckingRepository.findById(id);
        if (stChecking.isPresent()) {
            return stChecking.get().getBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Checking account not found.");
    }

    public Money getCreditCardBalance(Long id) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        if (creditCard.isPresent()) {
            return creditCard.get().getBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found.");
    }
}
