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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


//  This method has been created merely to add some sample data to the DB and, hence, to have the opportunity to test
//  the http requests.
    public void sampleData() {
        Role role = new Role("ADMIN");
        Role role1 = new Role("USER");
        roleRepository.save(role);
        roleRepository.save(role1);
        adminRepository.save(
                new Admin(
                        "dani",
                        "$2a$10$BOyKEH2lV31.TE4dmPdU1.BVJ/tE998zpZgxIxoEwucL6MTNbYkZW",
                        role,
                        "Dani Juan"
                )
        );
//      Trying different ways to persist the data.
        Optional<User> user = userRepository.findById(1L);
        role.setUser(user.get());
        roleRepository.save(role);
        Address address = new Address("Calle blabla", "Sant Just Desvern", "08960");
        Address address1 = new Address("Calle bleble", "Rubí", "08191");
        AccountHolder accountHolder = new AccountHolder("dejohak",
                "$2a$10$BOyKEH2lV31.TE4dmPdU1.BVJ/tE998zpZgxIxoEwucL6MTNbYkZW",
                role1, "Dani Juan", new Date(93, 7, 14), address);
        AccountHolder accountHolder1 = new AccountHolder("julieta",
                "$2a$10$5Hg0eqVOKmrohYmVl343CepXrW/zFvpD6c0dm1tQne0Ewpw4WSG2a",
                role1, "Júlia Galceran", new Date(95, 8, 7), address1);
        accountHolderRepository.save(accountHolder);
        accountHolderRepository.save(accountHolder1);
        user = userRepository.findById(2L);
        role1.setUser(user.get());
        roleRepository.save(role1);
        roleRepository.save(new Role("USER", accountHolder1));

//  =================== Accounts ===================
        Checking checking = new Checking(new Money(new BigDecimal(873.45)), accountHolder);
        List<Account> accountList = new ArrayList<>();
        accountList.add(checking);
        checkingRepository.save(checking);
        accountHolder.setAccounts(accountList);
        accountHolderRepository.save(accountHolder);
    }


    @Override
    public void run(String... args) throws Exception {
        sampleData();
    }
}
