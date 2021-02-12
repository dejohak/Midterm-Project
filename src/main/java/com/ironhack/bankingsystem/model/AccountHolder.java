package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.classes.Address;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User{
    private String name;
    private Date birthDate;
    @Embedded
    private Address primaryAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "mailing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postal_code"))
    })
    private Address mailingAddress;

    @OneToOne(mappedBy = "primaryOwner")
    private Account account;


    public AccountHolder() {
    }

    public AccountHolder(String username, String password, Role role, String name, Date birthDate,
                         Address primaryAddress) {
        super(username, password, role);
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
    }

    public AccountHolder(String name, Date birthDate, Address primaryAddress, Account account) {
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.account = account;
    }

    public AccountHolder(String name, Date birthDate, Address primaryAddress, Address mailingAddress, Account account) {
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.account = account;
    }

    public AccountHolder(String username, String password, Role role, String name, Date birthDate,
                         Address primaryAddress, Address mailingAddress, Account account) {
        super(username, password, role);
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.account = account;
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

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
}
