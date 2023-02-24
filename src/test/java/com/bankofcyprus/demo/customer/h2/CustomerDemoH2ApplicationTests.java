package com.bankofcyprus.demo.customer.h2;

import com.bankofcyprus.demo.customer.h2.controller.AccountController;
import com.bankofcyprus.demo.customer.h2.controller.CustomerController;
import com.bankofcyprus.demo.customer.h2.repository.AccountRepository;
import com.bankofcyprus.demo.customer.h2.repository.CountryRepository;
import com.bankofcyprus.demo.customer.h2.repository.CustomerAccountRepository;
import com.bankofcyprus.demo.customer.h2.repository.CustomerRepository;
import com.bankofcyprus.demo.customer.h2.service.AccountService;
import com.bankofcyprus.demo.customer.h2.service.CustomerService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerDemoH2ApplicationTests {

	@Autowired
	AccountController accountController;

	@Autowired
	CustomerController customerController;

	@Autowired
	CustomerService customerService;

	@Autowired
	AccountService accountService;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	CustomerAccountRepository customerAccountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Test
	void contextLoads() {
		//Smoke Tests
		assertThat(accountController).isNotNull();
		assertThat(customerController).isNotNull();
		assertThat(customerService).isNotNull();
		assertThat(accountService).isNotNull();
		assertThat(countryRepository).isNotNull();
		assertThat(customerAccountRepository).isNotNull();
		assertThat(customerRepository).isNotNull();
	}

}
