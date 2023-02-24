package com.bankofcyprus.demo.customer.h2.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectAccountRequest {

    @NotNull
    @Pattern(regexp = "\\d")
    @Size(min = 12, max = 12)
    private String accountNumber;
}
