package com.ironhack.bankingsystem.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Account {
    @Id
    private Long id;
    @OneToOne
    private AccountHolder primaryOwner;
    @OneToOne
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "secondary_owner_id")),
            @AttributeOverride(name = "name", column = @Column(name = "secondary_owner_name")),
            @AttributeOverride(name = "birthDate", column = @Column(name = "secondary_owner_name")),
            @AttributeOverride(name = "primaryAddress", column = @Column(name = "secondary_owner_primary_address")),
            @AttributeOverride(name = "mailingAddress", column = @Column(name = "secondary_owner_mailing_address"))
    })
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

    public Account(Long id, BigDecimal penaltyFee) {
        setId(id);
        setPenaltyFee(penaltyFee);
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

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
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
