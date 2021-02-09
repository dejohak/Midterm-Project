package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.classes.Address;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
public class AccountHolder {
    @Id
    private String id;
    private String name;
    private Date birthDate;
    @Embedded
    private Address primaryAddress;
    @Embedded
    private Optional<Address> secondaryAddress;

    public AccountHolder() {
    }

    public AccountHolder(String id, String name, Date birthDate, Address primaryAddress, Optional<Address> secondaryAddress) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.secondaryAddress = secondaryAddress;
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

    public Optional<Address> getSecondaryAddress() {
        return secondaryAddress;
    }

    public void setSecondaryAddress(Optional<Address> secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
    }
}
