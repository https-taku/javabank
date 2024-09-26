package com.cbs.javabank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreditDebitRequest {
    @Getter
    private String accountnumber;
    private BigDecimal amount;

}
