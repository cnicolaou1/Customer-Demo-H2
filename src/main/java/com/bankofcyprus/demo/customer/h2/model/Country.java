package com.bankofcyprus.demo.customer.h2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "Country",
        uniqueConstraints = { @UniqueConstraint(name = "UQ_COUNTRY_CODE", columnNames = { "code" } )}
)
public class Country {
    @Builder.Default
    @Id
    private String id= UUID.randomUUID().toString();
    @Version
    private Long version;
    
    @NotNull
    @Column(nullable = false)
    private String code;

    @NotNull
    @Column(nullable = false)
    private String name;
}
