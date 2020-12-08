package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {
    private Long idFrom; //who is withdrawing
    private Long idTo; //who is receiving
    private BigDecimal amount; //amount transferred


    public Long getIdFrom() {
        return idFrom;
    }







    public void setIdFrom(Long idFrom) {
        this.idFrom = idFrom;
    }

    public Long getIdTo() {
        return idTo;
    }

    public void setIdTo(Long idTo) {
        this.idTo = idTo;
    }



    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "idFrom=" + idFrom +
                ", idTo=" + idTo +
                ", amount=" + amount +
                '}';
    }
}
