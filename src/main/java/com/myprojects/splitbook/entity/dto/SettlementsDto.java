package com.myprojects.splitbook.entity.dto;

import java.math.BigDecimal;

public class SettlementsDto {

    private String info;
    private BigDecimal amount;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
