package com.bankofcyprus.demo.customer.h2.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.bankofcyprus.demo.customer.h2.api.dto.CreateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.api.dto.CustomerDto;
import com.bankofcyprus.demo.customer.h2.api.dto.UpdateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.config.SecurityConfigBean;
import com.bankofcyprus.demo.customer.h2.exception.CustomerUpdateException;
import com.bankofcyprus.demo.customer.h2.model.Account;
import com.bankofcyprus.demo.customer.h2.model.Country;
import com.bankofcyprus.demo.customer.h2.model.Customer;
import com.bankofcyprus.demo.customer.h2.model.CustomerAccount;
import com.bankofcyprus.demo.customer.h2.model.Segmentation;
import com.bankofcyprus.demo.customer.h2.model.Sex;
import com.bankofcyprus.demo.customer.h2.repository.AccountRepository;
import com.bankofcyprus.demo.customer.h2.repository.CountryRepository;
import com.bankofcyprus.demo.customer.h2.repository.CustomerAccountRepository;
import com.bankofcyprus.demo.customer.h2.repository.CustomerRepository;

@SpringBootTest(classes = {CustomerService.class})
@ContextConfiguration(classes = {SecurityConfigBean.class})
class CustomerServiceTests {

	@Autowired
	CustomerService service;
	
    @MockBean
    CustomerRepository repository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    CountryRepository countryRepository;

    @MockBean
    CustomerAccountRepository customerAccountRepository;

    @Test
    void findCustomer(){
        String userId = "123456";
        Customer customer = this.mockCustomer(userId);
        
        when(repository.findByUserId(userId)).thenReturn(customer);
        
        CustomerDto customerDto = service.findCustomer(userId);
        
        checkCustomerDto(customerDto, customer);
    }
    
    @Test
    void findAllCustomers(){
        String userId = "123456";
        Customer customer = this.mockCustomer(userId);
        
        when(repository.findAll()).thenReturn(Arrays.asList(customer));
        
        List<CustomerDto> customers = service.findAllCustomers();
        
        Assertions.assertNotNull(customers);
        Assertions.assertEquals(1, customers.size());

        checkCustomerDto(customers.get(0), customer);
    }

    @Test
    void createCustomer(){
        Customer customer = this.mockCustomer(null);
        CreateCustomerRequest request = this.mockCustomerRequest();

        when(countryRepository.findByCode(request.getCountryCode())).thenReturn(customer.getCountry());

        CustomerDto customerDto = service.createCustomer(request);

        checkCustomerDto(customerDto, customer);
    }
    
    @Test
    void updateCustomer() {
    	String userId = "123456";
        Customer customer = this.mockCustomer(userId);
        UpdateCustomerRequest request = this.mockUpdateCustomerRequest();

        when(repository.findByUserId(userId)).thenReturn(customer);
        CustomerDto customerDto = service.updateCustomer(userId, request);
        checkCustomerDto(customerDto, customer);

        request.setAuthCode("1111");
        request.setMobileNumber("9876543");
        Assertions.assertThrowsExactly(CustomerUpdateException.class, () -> service.updateCustomer(userId, request));
    }
    
    @Test
    void connectAccount() {
    	String userId = "123456";
    	String accountNumber = "9876543";
        Customer customer = this.mockCustomer(userId);
        Account account = this.mockAccount(accountNumber);
        
        when(repository.findByUserId(userId)).thenReturn(customer);
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(account);
        
        service.connectAccount(userId, accountNumber);   
    }

    void checkCustomerDto(CustomerDto customerDto, Customer customer) {
    	Assertions.assertNotNull(customerDto);
    	
    	//case of new customer/userId auto generated
    	if(customer.getUserId()==null) {
    		Assertions.assertNotNull(customerDto.getUserId());
    	}else {
    		Assertions.assertEquals(customerDto.getUserId(),customer.getUserId());
    	}
        
        Assertions.assertEquals(customerDto.getEmail(),customer.getEmail());
        Assertions.assertEquals(customerDto.getMobileNumber(),customer.getMobileNumber());
        Assertions.assertEquals(customerDto.getIdNumber(),customer.getIdNumber());
        Assertions.assertEquals(customerDto.getSex(),customer.getSex());
        Assertions.assertEquals(customerDto.getBirthDate(),customer.getBirthDate());
        Assertions.assertEquals(customerDto.getSegmentation(),customer.getSegmentation());
        Assertions.assertEquals(customerDto.getCountryCode(),customer.getCountry().getCode());
    }

    private Customer mockCustomer(String userId){
        return Customer.builder()
                .userId(userId)
                .segmentation(Segmentation.RETAIL)
                .firstName("Test")
                .middleName("Test")
                .lastName("Test")
                .email("test@test.com")
                .idNumber("123456")
                .country(new Country("123", 0L, "CY", "CYPRUS"))
                .mobileNumber("123456")
                .sex(Sex.FEMALE)
                .birthDate(LocalDate.of(2000,01,01))
                .customerAccounts(new ArrayList<CustomerAccount>())
                .build();
    }
    
    private Account mockAccount(String accountNumber){
        return Account.builder()
                .accountNumber(accountNumber)
                .accountType("TYPE")
                .currentBalance(BigDecimal.ZERO)
                .availableBalance(BigDecimal.ZERO)
                .version(0L)
                .openingDate(LocalDate.of(2000,01,01))
                .build();
    }

    private CreateCustomerRequest mockCustomerRequest(){
        return CreateCustomerRequest.builder()
                .segmentation(Segmentation.RETAIL)
                .firstName("Test")
                .middleName("Test")
                .lastName("Test")
                .email("test@test.com")
                .idNumber("123456")
                .countryCode("123")
                .mobileNumber("123456")
                .sex(Sex.FEMALE)
                .birthDate(LocalDate.of(2000,01,01))
                .build();
    }
    
    private UpdateCustomerRequest mockUpdateCustomerRequest(){
        return UpdateCustomerRequest.builder()
                .segmentation(Segmentation.RETAIL)
                .email("test@test.com")
                .mobileNumber("987654")
                .authCode("1234")
                .build();
    }
}
