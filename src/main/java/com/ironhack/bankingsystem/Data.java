package com.ironhack.bankingsystem;

import com.ironhack.bankingsystem.classes.Address;
import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.enums.Status;
import com.ironhack.bankingsystem.model.*;
import com.ironhack.bankingsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class Data implements CommandLineRunner {
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;

    public void sampleData() {


        Account account = new Account(2343545232243345L, new BigDecimal(40));

        AccountHolder accountHolder = new AccountHolder("478123456V", "Leo Messi",
                new Date(87, 5, 24),
                new Address("Calle Falsa 123", "Rosario", "08940"),
                account);

        Checking checking = new Checking("CH3124123556", new Money(new BigDecimal("853.89")),
                "1r0nh4ck3r", Status.ACTIVE, account);

        CreditCard creditCard = new CreditCard(455698842367128L, new Money(new BigDecimal(250)),
                account);


        accountRepository.save(account);
        accountHolderRepository.save(accountHolder);
        checkingRepository.save(checking);
        creditCardRepository.save(creditCard);
    }


    @Override
    public void run(String... args) throws Exception {
        sampleData();
    }
}
