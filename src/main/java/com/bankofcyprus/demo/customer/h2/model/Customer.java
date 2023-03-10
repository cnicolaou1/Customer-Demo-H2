package com.bankofcyprus.demo.customer.h2.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = { @UniqueConstraint(name = "UQ_CUSTOMER_USER_ID", columnNames = { "userId" } )},
        indexes = {@Index(name = "INX_CUSTOMER_MOBILE_NUMBER", columnList = "mobileNumber"),
                    @Index(name = "INX_CUSTOMER_EMAIL", columnList = "email")}
)
public class Customer {
    @Builder.Default
    @Id
    private String id= UUID.randomUUID().toString();
    @Version
    @NotNull
    private Long version;

    @NotNull
    private String userId;

    @NotNull
	private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Sex sex;

    private String email;

    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    private Segmentation segmentation;

    @NotNull
    private String idNumber;

    @NotNull
    private LocalDate birthDate;

    @ManyToOne(optional = false)
    private Country country;

    @OneToMany(cascade={CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "customer_id")
    private List<CustomerAccount> customerAccounts;
}