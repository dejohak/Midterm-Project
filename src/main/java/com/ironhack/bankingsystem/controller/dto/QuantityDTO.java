package com.ironhack.bankingsystem.controller.dto;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

public class QuantityDTO {
    @Digits(integer = 6, fraction = 2, message = "Quantity format not valid")
    private BigDecimal quantity;

    public QuantityDTO() {
    }

    public QuantityDTO(@Digits(integer = 6, fraction = 2, message = "Quantity format not valid") BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
