package com.ironhack.bankingsystem.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.bankingsystem.classes.Address;
import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.CheckingDTO;
import com.ironhack.bankingsystem.controller.dto.QuantityDTO;
import com.ironhack.bankingsystem.controller.dto.TransferThirdPartyDTO;
import com.ironhack.bankingsystem.model.*;
import com.ironhack.bankingsystem.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BankingSystemControllerTest {

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
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
        checking.setSecretKey(345123);
        checking.setId(231423534677234L);
        checking1.setId(231423534677534L);
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
        creditCard.setId(435657823123612345L);
        creditCardRepository.save(creditCard);
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        userRepository.deleteAll();
        studentCheckingRepository.deleteAll();
        checkingRepository.deleteAll();
        savingsRepository.deleteAll();
        creditCardRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void accessAccount_ValidSecretKey_AccountInfo() throws Exception{
        MvcResult result = mockMvc.perform(get("/access-account/345123")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("dejohak"));
    }


    @Test
    void accessAccountAdmin_ValidId_AccountInfo() throws Exception {
        MvcResult result = mockMvc.perform(get("/access-account/admin/231423534677234")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("dejohak"));
    }

    @Test
    void getAccountHolder_ValidId_AccountHolderInfo() throws Exception {
        MvcResult result = mockMvc.perform(get("/account-holder/2")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Leo Messi"));
    }

    @Test
    void createAccountHolder_ValidInfo_Created() throws Exception{
        Address address1 = new Address("Carretera Reial", "Sant Just", "08960");
        AccountHolder accountHolder = new AccountHolder("tester", "test",
                new Role("USER"), "Tester Tests", new Date(120, 2, 3), address1);
        String body = objectMapper.writeValueAsString(accountHolder);
        MvcResult result = mockMvc.perform(post("/create/account-holder")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Sant Just"));

    }

    @Test
    void createThirdParty_ValidInfo_Created() throws Exception{
        ThirdParty thirdParty = new ThirdParty("netflix_tp", "netflis",
                new Role("THIRD_PARTY"), "Netflix");
        String body = objectMapper.writeValueAsString(thirdParty);
        MvcResult result = mockMvc.perform(post("/create/third-party")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Netflix"));
    }

    @Test
    void getAccountBalance_ValidSecretKey_Balance() throws Exception{
        MvcResult result = mockMvc.perform(get("/get/account-balance/345123")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("873.45"));
    }

    @Test
    void getCreditCardBalance_ValidId_Balance() throws Exception {
        MvcResult result = mockMvc.perform(get("/get/credit-card-balance/435657823123612345")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("100.00"));
    }

    @Test
    void accounts_NoParams_ListOfAccounts() throws Exception {
        MvcResult result = mockMvc.perform(get("/show-accounts")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("100.00"));
    }

    @Test
    void transferMoney_AccountsIds_MoneyTransferred() throws Exception {
        QuantityDTO quantityDTO = new QuantityDTO(new BigDecimal(250.00));
        String body = objectMapper.writeValueAsString(quantityDTO);
        MvcResult result = mockMvc.perform(patch("/transfer/231423534677234/231423534677534")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(623.45, checkingRepository.findById(231423534677234L).get().getBalance().getAmount().doubleValue());
    }

    @Test
    void createChecking_Valid_CheckingCreated() throws Exception{
        CheckingDTO checkingDTO = new CheckingDTO(new BigDecimal(322.67), 2L);
        String body = objectMapper.writeValueAsString(checkingDTO);
        MvcResult result = mockMvc.perform(post("/create/checking")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("322.67"));
    }

    @Test
    void creditAccountBalance_Id_BalanceIncreased() throws Exception{
        QuantityDTO quantityDTO = new QuantityDTO(new BigDecimal(250.00));
        String body = objectMapper.writeValueAsString(quantityDTO);
        MvcResult result = mockMvc.perform(patch("/credit/account/231423534677234")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(1123.45, checkingRepository.findById(231423534677234L).get().getBalance().getAmount().doubleValue());

    }

    @Test
    void debitAccountBalance_Valid_BalanceDecreased() throws Exception {
        QuantityDTO quantityDTO = new QuantityDTO(new BigDecimal(250.00));
        String body = objectMapper.writeValueAsString(quantityDTO);
        MvcResult result = mockMvc.perform(patch("/debit/account/231423534677234")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(623.45, checkingRepository.findById(231423534677234L).get().getBalance().getAmount().doubleValue());
    }

    @Test
    void creditAccountTP_Valid_BalanceIncreased() throws Exception {
        TransferThirdPartyDTO transferThirdPartyDTO = new TransferThirdPartyDTO(new BigDecimal(250.00),
                231423534677234L);
        String body = objectMapper.writeValueAsString(transferThirdPartyDTO);
        MvcResult result = mockMvc.perform(patch("/creditTP/account/-1911368973")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(1123.45, checkingRepository.findById(231423534677234L).get().getBalance().getAmount()
                .doubleValue());
    }

    @Test
    void debitAccountTP_Valid_BalanceDecreased() throws Exception {
        TransferThirdPartyDTO transferThirdPartyDTO = new TransferThirdPartyDTO(new BigDecimal(250.00),
                231423534677234L);
        String body = objectMapper.writeValueAsString(transferThirdPartyDTO);
        MvcResult result = mockMvc.perform(patch("/debitTP/account/-1911368973")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(623.45, checkingRepository.findById(231423534677234L).get().getBalance().getAmount()
                .doubleValue());
    }
}