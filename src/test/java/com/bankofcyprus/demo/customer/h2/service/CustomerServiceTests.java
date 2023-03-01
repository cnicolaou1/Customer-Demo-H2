package com.bankofcyprus.demo.customer.h2.service;

import com.bankofcyprus.demo.customer.h2.api.dto.CreateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.api.dto.CustomerDto;
import com.bankofcyprus.demo.customer.h2.api.dto.UpdateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.exception.CustomerUpdateException;
import com.bankofcyprus.demo.customer.h2.model.*;
import com.bankofcyprus.demo.customer.h2.repository.AccountRepository;
import com.bankofcyprus.demo.customer.h2.repository.CountryRepository;
import com.bankofcyprus.demo.customer.h2.repository.CustomerAccountRepository;
import com.bankofcyprus.demo.customer.h2.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTests {
	CustomerService service;
	
    @Mock
    CustomerRepository repository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    CountryRepository countryRepository;

    @Mock
    CustomerAccountRepository customerAccountRepository;

    @BeforeEach
    void initUseCase() {
        service = new CustomerService("1234", true, repository, accountRepository, countryRepository);
    }
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
        
        List<CustomerDto> customers = service.findAllCustomers().getCustomers();
        
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
