package com.bankofcyprus.demo.customer.h2.repository;

import com.bankofcyprus.demo.customer.h2.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	public Customer findByIdNumber(String idNumber);

	public Customer findByUserId(String userId);

	@Query("select c " +
			" from Customer c " +
			"inner join fetch c.customerAccounts ca " +
			"inner join fetch ca.account a " +
			"where a.accountNumber = ?1")
	public Customer findByAccountNumber(String accountNumber);

	public Customer findByCountryCode(String code);
}
