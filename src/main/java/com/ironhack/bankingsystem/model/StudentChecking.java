package com.ironhack.bankingsystem.model;

import com.ironhack.bankingsystem.classes.Money;
import com.ironhack.bankingsystem.enums.Status;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account{
    private Integer secretKey;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public StudentChecking() {
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
        this.secretKey = (int) (Math.random()*1000000);
        this.status = Status.ACTIVE;
    }
    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = (int) (Math.random()*1000000);
        this.status = Status.ACTIVE;
    }


    public Integer getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Integer secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
