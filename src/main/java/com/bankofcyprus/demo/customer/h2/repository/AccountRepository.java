package com.bankofcyprus.demo.customer.h2.repository;

import com.bankofcyprus.demo.customer.h2.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	public Account findByAccountNumber(String accountNumber);
}
