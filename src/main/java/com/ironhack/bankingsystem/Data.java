package com.ironhack.bankingsystem;

import com.ironhack.bankingsystem.classes.Address;
import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.enums.Status;
import com.ironhack.bankingsystem.model.*;
import com.ironhack.bankingsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;


//  This method has been created merely to add some sample data to the DB and, hence, to have the opportunity to test
//  the http requests.
    public void sampleData() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Role role = new Role("ADMIN");
        Role role1 = new Role("USER");
        Role role2 = new Role("THIRD_PARTY");
        roleRepository.save(role);
        roleRepository.save(role1);
        roleRepository.save(role2);
        adminRepository.save(
                new Admin(
                        "dani",
                        passwordEncoder.encode("admin"),
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
                passwordEncoder.encode("ironhacker"),
                role1, "Leo Messi", new Date(93, 7, 14), address);
        AccountHolder accountHolder1 = new AccountHolder("julieta",
                passwordEncoder.encode("iepa"),
                role1, "Júlia Galceran", new Date(95, 8, 7), address1);
        accountHolderRepository.save(accountHolder);
        accountHolderRepository.save(accountHolder1);
        user = userRepository.findById(2L);
        role1.setUser(user.get());
        roleRepository.save(role1);
        roleRepository.save(new Role("USER", accountHolder1));

        ThirdParty thirdParty = new ThirdParty("paypal_tp", passwordEncoder.encode("paypal"),
                role2, "PayPal");
        thirdPartyRepository.save(thirdParty);
        user = userRepository.findById(4L);
        role2.setUser(user.get());
        roleRepository.save(role2);


//  =================== Accounts ===================
        Checking checking = new Checking(new Money(new BigDecimal(873.45)), accountHolder);
        Checking checking1 = new Checking(new Money(new BigDecimal(1004.82)), accountHolder1);
        List<Account> accountList = new ArrayList<>();
        accountList.add(checking);
        accountList.add(checking1);
        checkingRepository.save(checking);
        checkingRepository.save(checking1);
        accountHolder.setAccounts(accountList);
        accountHolderRepository.save(accountHolder);
        accountHolderRepository.save(accountHolder1);
        AccountHolder accountHolder2 = new AccountHolder("tester", passwordEncoder.encode("testing"),
                role1, "Tester Tests", new Date(120, 2, 3), address1);
        accountHolderRepository.save(accountHolder2);
        roleRepository.save(new Role("USER", accountHolder2));
        StudentChecking studentChecking = new StudentChecking(new Money(new BigDecimal(358.12)), accountHolder2);
        accountList.add(studentChecking);
        studentCheckingRepository.save(studentChecking);
        Savings savings = new Savings(new Money(new BigDecimal(1304.76)), accountHolder2);
        accountList.add(savings);
        savingsRepository.save(savings);
        CreditCard creditCard = new CreditCard(new Money(new BigDecimal(139.00)), accountHolder2);
        accountList.add(creditCard);
        creditCardRepository.save(creditCard);


    }


    @Override
    public void run(String... args) throws Exception {
        sampleData();
    }
}
