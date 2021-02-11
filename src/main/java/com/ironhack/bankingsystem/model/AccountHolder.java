package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.classes.Address;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AccountHolder {
    @Id
    private String id;
    private String name;
    private Date birthDate;
    @Embedded
    private Address primaryAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "secondary_street")),
            @AttributeOverride(name = "city", column = @Column(name = "secondary_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "secondary_postal_code"))
    })
    private Address secondaryAddress;

    @OneToOne
    private Account account;

    @OneToOne
    private User user;


    public AccountHolder() {
    }

    public AccountHolder(String id, String name, Date birthDate, Address primaryAddress, Account account) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.account = account;
    }

    public AccountHolder(String id, String name, Date birthDate, Address primaryAddress, Address secondaryAddress, Account account) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.secondaryAddress = secondaryAddress;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getSecondaryAddress() {
        return secondaryAddress;
    }

    public void setSecondaryAddress(Address secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
    }
}
