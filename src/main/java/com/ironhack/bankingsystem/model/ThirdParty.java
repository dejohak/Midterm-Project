package com.ironhack.bankingsystem.model;


import com.ironhack.bankingsystem.utils.PasswordUtil;

import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ThirdParty extends User{
    private String name;
    private String hashedKey;

    public ThirdParty() {
    }

    public ThirdParty(String name) {
        setName(name);
        this.hashedKey = PasswordUtil.encryptPassword(name);
    }

    public ThirdParty(String username, String password, Role role, String name) {
        super(username, password, role);
        this.name = name;
        this.hashedKey = PasswordUtil.encryptPassword(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedKey() {
        return hashedKey;
    }

}
