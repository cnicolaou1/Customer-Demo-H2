package com.bankofcyprus.demo.customer.h2.api.dto;

import com.bankofcyprus.demo.customer.h2.model.Segmentation;
import com.bankofcyprus.demo.customer.h2.model.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String middleName;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @NotNull
    @Email
    @Size(max = 250, min = 4)
    private String email;

    @NotNull
    @Pattern(regexp = "\\d+", message = "A Valid Mobile Number must be provided!")
    private String mobileNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Segmentation segmentation;

    @NotNull
    @Size(max = 10)
    private String idNumber;

    @NotNull
    @Past
    private LocalDate birthDate;

    @Size(max = 2, min = 2)
    private String countryCode;
}
