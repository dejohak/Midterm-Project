package com.ironhack.bankingsystem.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Account {
    @Id
    private Long id;
    @OneToOne(mappedBy = "account")
    private AccountHolder primaryOwner;
    @OneToOne(mappedBy = "account")
    private AccountHolder secondaryOwner;
    private BigDecimal penaltyFee;


    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Checking> checkingList;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudentChecking> studentCheckingList;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CreditCard> creditCards;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Savings> savingsList;


    public Account() {
    }

    public Account(Long id, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   BigDecimal penaltyFee) {
        this.id = id;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = penaltyFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryAccountHolder) {
        this.primaryOwner = primaryAccountHolder;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public List<Checking> getCheckingList() {
        return checkingList;
    }

    public void setCheckingList(List<Checking> checkingList) {
        this.checkingList = checkingList;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }
}
