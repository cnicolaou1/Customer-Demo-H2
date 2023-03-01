package com.bankofcyprus.demo.customer.h2.api.dto;

import java.time.LocalDate;

import com.bankofcyprus.demo.customer.h2.model.Segmentation;
import com.bankofcyprus.demo.customer.h2.model.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String userId;

    private String name;

    private Sex sex;

    private String email;

    private String mobileNumber;

    private String idNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Segmentation segmentation;

    private String countryCode;
}
