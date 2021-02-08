package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.classes.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ThirdParty extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hashedKey;

    public ThirdParty(String name) {
        super(name);
    }

    public ThirdParty(String name, String hashedKey) {
        super(name);
        this.hashedKey = hashedKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
