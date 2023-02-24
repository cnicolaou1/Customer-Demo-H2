package com.bankofcyprus.demo.customer.h2.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bankofcyprus.demo.customer.h2.model.Segmentation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest {

    @Email
    @Size(max = 250, min = 4)
    private String email;

    @Pattern(regexp = "\\d")
    private String mobileNumber;

    private Segmentation segmentation;

    @Size(max = 4)
    private String authCode;
}
