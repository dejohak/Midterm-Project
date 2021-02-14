package com.ironhack.bankingsystem.service.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.AccountHolderDTO;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.model.*;
import com.ironhack.bankingsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BankingSystemService {
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
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    public AccountHolder findAccountHolder(Long id) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(id);
        if (accountHolder.isPresent()) {
            return accountHolder.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found");
    }

    public List<Checking> getAccounts() {
        return checkingRepository.findAll();
    }

    public Money getCheckingBalance(Integer secretKey) {
        Optional<Checking> checking = checkingRepository.findBySecretKey(secretKey);
        if (checking.isPresent()) {
            return checking.get().getBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found.");
    }

    public Money getStudentCheckingBalance(Integer secretKey) {
        Optional<StudentChecking> stChecking = studentCheckingRepository.findBySecretKey(secretKey);
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

    public Money getSavingsBalance(Integer secretKey) {
        Optional<Savings> savings = savingsRepository.findBySecretKey(secretKey);
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

    public ThirdParty createThirdParty(String name, String password) {
        Role role = new Role("THIRD_PARTY");
        roleRepository.save(role);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ThirdParty thirdParty = new ThirdParty(name.toLowerCase() + "_tp", passwordEncoder.encode(password),
                role, name);
        thirdPartyRepository.save(thirdParty);
        role.setUser(thirdParty);
        roleRepository.save(role);
        return thirdParty;
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
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found.");
    }

    public void creditAccountBalance(Long id, BigDecimal quantity) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Money balance;
            balance = new Money(account.get().getBalance().increaseAmount(quantity),
                        account.get().getBalance().getCurrency());
            account.get().setBalance(balance);
            accountRepository.save(account.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
    }

    public void debitAccountBalance(Long id, BigDecimal quantity) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Money balance;
            balance = new Money(account.get().getBalance().decreaseAmount(quantity),
                    account.get().getBalance().getCurrency());
            account.get().setBalance(balance);
            accountRepository.save(account.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
    }


    public void transferMoney(Long id, Long targetId, BigDecimal amount) {
        Optional<Account> account = accountRepository.findById(id);
        Optional<Account> targetAccount = accountRepository.findById(targetId);
        if (account.isPresent()) {
            if (targetAccount.isPresent()) {
                if(account.get().getBalance().getAmount().doubleValue() >= amount.doubleValue()) {
                    account.get().setBalance(new Money(account.get().getBalance().decreaseAmount(amount)));
                    accountRepository.save(account.get());
                    targetAccount.get().setBalance(new Money(targetAccount.get().getBalance().increaseAmount(amount)));
                    accountRepository.save(targetAccount.get());
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough funds");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Target account not found.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
    }

    public List<Account> showAccounts() {
        return accountRepository.findAll();
    }

    public void creditAccountTP(BigDecimal amount, Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Money balance;
            balance = new Money(account.get().getBalance().increaseAmount(amount),
                    account.get().getBalance().getCurrency());
            account.get().setBalance(balance);
            accountRepository.save(account.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
    }

    public void debitAccountTP(BigDecimal amount, Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Money balance;
            balance = new Money(account.get().getBalance().decreaseAmount(amount),
                    account.get().getBalance().getCurrency());
            account.get().setBalance(balance);
            accountRepository.save(account.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
    }
}
