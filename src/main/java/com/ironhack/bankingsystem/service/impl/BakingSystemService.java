package com.ironhack.bankingsystem.service.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.AccountDTO;
import com.ironhack.bankingsystem.controller.dto.AccountHolderDTO;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.model.*;
import com.ironhack.bankingsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BakingSystemService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO) {
        if (!accountHolderDTO.getRole().equals("USER")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account holders can only be USER");
        } else {
            AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getUsername(), accountHolderDTO.getPassword(),
                    accountHolderDTO.getRole(), accountHolderDTO.getName(), accountHolderDTO.getBirthDate(),
                    accountHolderDTO.getPrimaryAddress());
            return accountHolderRepository.save(accountHolder);
        }
    }

    public Account createAccount(AccountDTO accountDTO) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(accountDTO.getAccountHolder().getId());
        if (accountHolder.isPresent()) {
            return accountRepository.save(new Account(accountDTO.getBalance(), accountHolder.get()));
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found.");
    }

    public Money getCheckingBalance(Long id) {
        Optional<Checking> checking = checkingRepository.findById(id);
        if (checking.isPresent()) {
            return checking.get().getBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found.");
    }

    public Money getStudentCheckingBalance(Long id) {
        Optional<StudentChecking> stChecking = studentCheckingRepository.findById(id);
        if (stChecking.isPresent()) {
            return stChecking.get().getBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Checking account not found.");
    }

    public Money getCreditCardBalance(Long id) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        if (creditCard.isPresent()) {
            return creditCard.get().getCreditCardBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found.");
    }

    public Object validateAgeToCreateAccount(CheckingDTO checkingDTO) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(checkingDTO.getAccountHolder().getId());
        Optional<Account> account = accountRepository.findById(checkingDTO.getId());
        if (accountHolder.isPresent() && account.isPresent()) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            LocalDateTime ldt = ts.toLocalDateTime();
            int checker = ldt.getYear() - accountHolder.get().getBirthDate().getYear() - 1900;
            if (checker < 24) {
                // st checking
                return studentCheckingRepository.save(new StudentChecking(checkingDTO.getId(), checkingDTO.getBalance(),
                        checkingDTO.getAccountHolder()));
            } else if (checker == 24) {
                if (ldt.getMonthValue() >= accountHolder.get().getBirthDate().getMonth()+1
                        && ldt.getDayOfMonth() >= accountHolder.get().getBirthDate().getDate()) {
                    // st checking
                    return studentCheckingRepository.save(new StudentChecking(checkingDTO.getId(), checkingDTO.getBalance(),
                            checkingDTO.getAccountHolder()));
                } else {
                    // checking
                    return checkingRepository.save(new Checking(checkingDTO.getId(), checkingDTO.getBalance(),
                            checkingDTO.getAccountHolder()));
                }
            } else {
                // checking
                return checkingRepository.save(new Checking(checkingDTO.getId(), checkingDTO.getBalance(),
                        checkingDTO.getAccountHolder()));
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account or Account holder not found.");
    }
}
