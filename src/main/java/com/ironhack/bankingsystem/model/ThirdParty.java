package com.ironhack.bankingsystem.model;


import com.ironhack.bankingsystem.utils.PasswordUtil;

import javax.persistence.*;

@Entity
public class ThirdParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String hashedKey;

    @OneToOne
    private User user;

    public ThirdParty() {
    }

    public ThirdParty(String name) {
        setName(name);
        this.hashedKey = PasswordUtil.encryptPassword(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
