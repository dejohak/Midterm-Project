package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.classes.Address;
import com.ironhack.bankingsystem.classes.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
public class AccountHolder extends User {
    private Date birthDate;
    @Embedded
    private Address primaryAddress;
    @Embedded
    private Optional<Address> secondaryAddress;


    public AccountHolder(String name) {
        super(name);
    }

    public AccountHolder(String name, Date birthDate, Address primaryAddress, Optional<Address> secondaryAddress) {
        super(name);
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.secondaryAddress = secondaryAddress;
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
