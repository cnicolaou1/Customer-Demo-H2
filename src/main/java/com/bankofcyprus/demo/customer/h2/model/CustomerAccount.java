package com.bankofcyprus.demo.customer.h2.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = { @UniqueConstraint(name = "UQ_CUSTOMER_ACCOUNT", columnNames = { "customer_id", "account_id" } )},
        indexes = {@Index(name = "INX_CUSTOMER_ACCOUNT_ACCOUNT", columnList = "account_id")}
)
public class CustomerAccount {
    @Builder.Default
    @Id
    private String id= UUID.randomUUID().toString();
    @Version
    private Long version;

    @ManyToOne(optional = false)
    @JoinColumn
    @NotNull
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn
    @NotNull
    private Account account;
}
