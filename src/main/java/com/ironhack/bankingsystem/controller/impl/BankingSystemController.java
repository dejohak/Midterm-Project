package com.ironhack.bankingsystem.controller.impl;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.controller.dto.*;
import com.ironhack.bankingsystem.controller.interfaces.IBankingSystemController;
import com.ironhack.bankingsystem.model.Account;
import com.ironhack.bankingsystem.model.AccountHolder;
import com.ironhack.bankingsystem.model.ThirdParty;
import com.ironhack.bankingsystem.service.impl.BankingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BankingSystemController implements IBankingSystemController {

    @Autowired
    private BankingSystemService bankingSystemService;

//  Allows account holders to access to their accounts by typing their secret key in the path
    @GetMapping("/access-account/{secretKey}")
    @ResponseStatus(HttpStatus.OK)
    public Object accessAccount(@PathVariable Integer secretKey) {
        return bankingSystemService.accessAccount(secretKey);
    }

//  Allows admins to access any account they want by typing the id of the account
    @GetMapping("/access-account/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object accessAccountAdmin(@PathVariable Long id) {
        return bankingSystemService.accessAccountAdmin(id);
    }

//  Allows admins to find the information of any account holder by typing its id
    @GetMapping("/account-holder/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolder(@PathVariable Long id) {
        return bankingSystemService.findAccountHolder(id);
    }

//  Allows admins to add a new account holder to the DB
    @PostMapping("/create/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        return bankingSystemService.createAccountHolder(accountHolderDTO);
    }
//  Admins must provide a name and a password to create the third party user. The username will be the third party's
//  name to lower case + "_tp", the password will be automatically encoded, and the hashed key will be set randomly.
    @PostMapping("/create/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        return bankingSystemService.createThirdParty(thirdPartyDTO.getName(), thirdPartyDTO.getPassword());
    }


    @GetMapping("/get/checking-balance/{secretKey}")
    @ResponseStatus(HttpStatus.OK)
    public Money getCheckingBalance(@PathVariable @Valid Integer secretKey) {
        return bankingSystemService.getCheckingBalance(secretKey);
    }

//  Methods to get the balance of an specified account
    @GetMapping("/get/student-checking-balance/{secretKey}")
    @ResponseStatus(HttpStatus.OK)
    public Money getStudentCheckingBalance(@PathVariable @Valid Integer secretKey) {
        return bankingSystemService.getStudentCheckingBalance(secretKey);
    }

    @GetMapping("/get/credit-card-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getCreditCardBalance(@PathVariable @Valid Long id) {
        return bankingSystemService.getCreditCardBalance(id);
    }

    @GetMapping("/get/savings-balance/{secretKey}")
    @ResponseStatus(HttpStatus.OK)
    public Money getSavingsBalance(@PathVariable @Valid Integer secretKey) {
        return bankingSystemService.getSavingsBalance(secretKey);
    }

//  Admins can see all the accounts in the DB with this method.
    @GetMapping("/show-accounts")
    public List<Account> accounts() {
        List<Account> accounts = bankingSystemService.showAccounts();
        return accounts;
    }

//  Account holders will be able to transfer any amount(if sufficient funds) to any account
    @PatchMapping("/transfer/{id}/{targetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferMoney(@PathVariable @Valid Long id, @PathVariable @Valid Long targetId,
                              @RequestBody @Valid QuantityDTO quantityDTO) {
        bankingSystemService.transferMoney(id, targetId, quantityDTO.getQuantity());
    }
//  Admins can create a checking account with this method. It will create a student checking account if the account
//  holder is 24 years old or less.
    @PostMapping("/create/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createChecking(@RequestBody @Valid CheckingDTO checkingDTO) {
        return bankingSystemService.validateAgeToCreateAccount(checkingDTO.getQuantity(), checkingDTO.getId());
    }

//  Admins can credit or debit an account with the following two methods
    @PatchMapping("/credit/account/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditAccountBalance(@PathVariable @Valid Long id, @RequestBody @Valid QuantityDTO quantity) {
        bankingSystemService.creditAccountBalance(id, quantity.getQuantity());
    }

    @PatchMapping("/debit/account/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitAccountBalance(@PathVariable @Valid Long id, @RequestBody @Valid QuantityDTO quantity) {
        bankingSystemService.debitAccountBalance(id, quantity.getQuantity());
    }

//  Third parties can debit or credit an account with the following two methods. They have to provide their hashed key
//  in the request.
    @PatchMapping("/creditTP/account/{hashedKey}")
    public void creditAccountTP(@PathVariable Integer hashedKey, @RequestBody @Valid TransferThirdPartyDTO transferThirdPartyDTO) {
        bankingSystemService.creditAccountTP(transferThirdPartyDTO.getAmount(), transferThirdPartyDTO.getId());
    }

    @PatchMapping("/debitTP/account/{hashedKey}")
    public void debitAccountTP(@PathVariable Integer hashedKey, @RequestBody @Valid TransferThirdPartyDTO transferThirdPartyDTO) {
        bankingSystemService.debitAccountTP(transferThirdPartyDTO.getAmount(), transferThirdPartyDTO.getId());
    }
}
