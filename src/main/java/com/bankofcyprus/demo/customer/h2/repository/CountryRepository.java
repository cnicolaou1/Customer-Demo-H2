package com.bankofcyprus.demo.customer.h2.repository;

import com.bankofcyprus.demo.customer.h2.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
    public Country findByCode(String code);
}
