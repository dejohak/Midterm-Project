package com.ironhack.bankingsystem.controller.dto;

import com.ironhack.bankingsystem.classes.Address;
import com.ironhack.bankingsystem.model.Role;

import java.util.Date;
import java.util.Set;

public class AccountHolderDTO {
    private String username;
    private String password;
    private Role role;
    private String name;
    private Date birthDate;
    private Address primaryAddress;

    public AccountHolderDTO(String username, String password, Role role, String name, Date birthDate, Address primaryAddress) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
}
