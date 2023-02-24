package com.bankofcyprus.demo.customer.h2.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String accountNumber;

    private String accountType;

    private BigDecimal availableBalance;

    private BigDecimal currentBalance;
}
