package com.bankofcyprus.demo.customer.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankofcyprus.demo.customer.h2.model.CustomerAccount;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, String> {
}
