package com.bankofcyprus.demo.customer.h2.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
        uniqueConstraints = { @UniqueConstraint(name = "UQ_ACCOUNT_ACCOUNT_NUMBER", columnNames = { "accountNumber" } )}
)
public class Account {
    @Builder.Default
    @Id
    private String id= UUID.randomUUID().toString();
    @Version
    @NotNull
    private Long version;
    
    @NotNull
    private String accountNumber;
    
    @NotNull
    private String accountType;
    
    @NotNull
    @Builder.Default
    private BigDecimal availableBalance=BigDecimal.ZERO;
    
    @NotNull
    @Builder.Default
    private BigDecimal currentBalance=BigDecimal.ZERO;
    
    @NotNull
    private LocalDate openingDate;

    @OneToMany(cascade={CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name="account_id")
    private List<CustomerAccount> customerAccounts;
}
