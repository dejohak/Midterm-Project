package com.ironhack.bankingsystem.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
public class CreditCard extends Account {
    @Id
    private Long creditCardId;
    private BigDecimal creditLimit;
    private Double interestRate;

    public CreditCard(Long id, BigDecimal balance, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner,
                      BigDecimal penaltyFee) {
        super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
        this.creditLimit = new BigDecimal(100);
        this.interestRate = 0.2;
    }

    public CreditCard(Long id, Long creditCardId, BigDecimal balance, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner,
                      BigDecimal penaltyFee, BigDecimal creditLimit, Double interestRate) {
        super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
        setCreditCardId(creditCardId);
        setInterestRate(interestRate);
        setCreditLimit(creditLimit);
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        if (creditLimit.doubleValue() > 100 && creditLimit.doubleValue() < 100000) {
            this.creditLimit = creditLimit;
        } else {
            System.err.println("Credit limit must be an amount between 100 and 100000. It will be set to its default " +
                    "value.");
            this.creditLimit = new BigDecimal(100);
        }
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        if (interestRate <= 0.2 && interestRate >= 0.1) {
            this.interestRate = interestRate;
        } else {
            System.err.println("Interest rate may be less than 0.2 but no lower than 0.1. It will be set to its " +
                    "default value.");
            this.interestRate = 0.2;
        }
    }
}
