package com.ironhack.bankingsystem.service.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.AccountHolderDTO;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.model.*;
import com.ironhack.bankingsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private RoleRepository roleRepository;

    public AccountHolder findAccountHolder(Long id) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(id);
        if (accountHolder.isPresent()) {
            return accountHolder.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found");
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

    public Money getSavingsBalance(Long id) {
        Optional<Savings> savings = savingsRepository.findById(id);
        if(savings.isPresent()) {
            return savings.get().getSavingsBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings account not found.");
    }

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO) {
        if (!accountHolderDTO.getRole().getName().equals("USER")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account holders can only be USER");
        } else {
            Role role = new Role("USER");
            roleRepository.save(role);
            AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getUsername(), accountHolderDTO.getPassword(),
                    role, accountHolderDTO.getName(), accountHolderDTO.getBirthDate(),
                    accountHolderDTO.getPrimaryAddress());
            accountHolderRepository.save(accountHolder);
            role.setUser(accountHolder);
            roleRepository.save(role);
            return accountHolder;
        }
    }

    public Object validateAgeToCreateAccount(CheckingDTO checkingDTO) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(checkingDTO.getAccountHolder().getId());
        if (accountHolder.isPresent()) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            LocalDateTime ldt = ts.toLocalDateTime();
            int checker = ldt.getYear() - accountHolder.get().getBirthDate().getYear() - 1900;
            if (checker < 24) {
                // st checking
                return studentCheckingRepository.save(new StudentChecking(checkingDTO.getBalance(),
                        checkingDTO.getAccountHolder()));
            } else if (checker == 24) {
                if (ldt.getMonthValue() >= accountHolder.get().getBirthDate().getMonth()+1
                        && ldt.getDayOfMonth() >= accountHolder.get().getBirthDate().getDate()) {
                    // st checking
                    return studentCheckingRepository.save(new StudentChecking(checkingDTO.getBalance(),
                            checkingDTO.getAccountHolder()));
                } else {
                    // checking
                    return checkingRepository.save(new Checking(checkingDTO.getBalance(), checkingDTO.getAccountHolder()));
                }
            } else {
                // checking
                return checkingRepository.save(new Checking(checkingDTO.getBalance(), checkingDTO.getAccountHolder()));
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account or Account holder not found.");
    }

    public void updateCheckingBalance(Long id, BigDecimal quantity) {
        Optional<Checking> checking = checkingRepository.findById(id);
        if (checking.isPresent()) {
            if (quantity.doubleValue() >= 0) {
                Money balance = new Money(checking.get().getBalance().increaseAmount(quantity),
                        checking.get().getBalance().getCurrency());
                checking.get().setBalance(balance);
            } else {
                Money balance = new Money(checking.get().getBalance().decreaseAmount(quantity),
                        checking.get().getBalance().getCurrency());
                checking.get().setBalance(balance);
            }
            checkingRepository.save(checking.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found.");
    }

    public void updateStudentCheckingBalance(Long id, BigDecimal quantity) {
        Optional<StudentChecking> studentChecking = studentCheckingRepository.findById(id);
        if (studentChecking.isPresent()) {
            if (quantity.doubleValue() >= 0) {
                Money balance = new Money(studentChecking.get().getBalance().increaseAmount(quantity),
                        studentChecking.get().getBalance().getCurrency());
                studentChecking.get().setBalance(balance);
            } else {
                Money balance = new Money(studentChecking.get().getBalance().decreaseAmount(quantity),
                        studentChecking.get().getBalance().getCurrency());
                studentChecking.get().setBalance(balance);
            }
            studentCheckingRepository.save(studentChecking.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Checking account not found.");
    }

    public void updateSavingsBalance(Long id, BigDecimal quantity) {
        Optional<Savings> savings = savingsRepository.findById(id);
        if (savings.isPresent()) {
            if (quantity.doubleValue() >= 0) {
                Money balance = new Money(savings.get().getBalance().increaseAmount(quantity),
                        savings.get().getBalance().getCurrency());
                savings.get().setBalance(balance);
            } else {
                Money balance = new Money(savings.get().getBalance().decreaseAmount(quantity),
                        savings.get().getBalance().getCurrency());
                savings.get().setBalance(balance);
            }
            savingsRepository.save(savings.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings account not found.");
    }

    public void updateCreditCardBalance(Long id, BigDecimal quantity) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        if (creditCard.isPresent()) {
            if (quantity.doubleValue() >= 0) {
                Money balance = new Money(creditCard.get().getBalance().increaseAmount(quantity),
                        creditCard.get().getBalance().getCurrency());
                creditCard.get().setBalance(balance);
            } else {
                Money balance = new Money(creditCard.get().getBalance().decreaseAmount(quantity),
                        creditCard.get().getBalance().getCurrency());
                creditCard.get().setBalance(balance);
            }
            creditCardRepository.save(creditCard.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found.");
    }
}
