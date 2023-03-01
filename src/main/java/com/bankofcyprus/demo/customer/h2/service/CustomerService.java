package com.bankofcyprus.demo.customer.h2.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bankofcyprus.demo.customer.h2.api.dto.CreateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.api.dto.CustomerDto;
import com.bankofcyprus.demo.customer.h2.api.dto.CustomerListDto;
import com.bankofcyprus.demo.customer.h2.api.dto.UpdateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.exception.ConnectAccountException;
import com.bankofcyprus.demo.customer.h2.exception.CustomerCreateException;
import com.bankofcyprus.demo.customer.h2.exception.CustomerUpdateException;
import com.bankofcyprus.demo.customer.h2.model.Account;
import com.bankofcyprus.demo.customer.h2.model.Customer;
import com.bankofcyprus.demo.customer.h2.model.CustomerAccount;
import com.bankofcyprus.demo.customer.h2.repository.AccountRepository;
import com.bankofcyprus.demo.customer.h2.repository.CountryRepository;
import com.bankofcyprus.demo.customer.h2.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService {
	private String authCode;

    private boolean authorizationCodeRequired;

    private CustomerRepository customerRepository;

    private AccountRepository accountRepository;

    private CountryRepository countryRepository;

    public CustomerDto findCustomer(String userId){
        Customer customer = customerRepository.findByUserId(userId);

        return toCustomerDto(customer);
    }

    public CustomerListDto findAllCustomers(){
        List<Customer> customers = customerRepository.findAll();

        return CustomerListDto.builder().customers(customers.stream()
                .map(customer -> toCustomerDto(customer)).collect(Collectors.toList())).build();
    }

    public CustomerDto createCustomer(CreateCustomerRequest request){
        Random random = new Random();
        Customer customer = customerRepository.findByIdNumber(request.getIdNumber());

        if(customer!=null){
            throw new CustomerCreateException("Another Customer with the Same Id Exists!");
        }

        customer = Customer.builder()
                    .mobileNumber(request.getMobileNumber())
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .middleName(request.getMiddleName())
                    .lastName(request.getLastName())
                    .idNumber(request.getIdNumber())
                    .segmentation(request.getSegmentation())
                    .sex(request.getSex())
                    .birthDate(request.getBirthDate())
                    .country(countryRepository.findByCode(request.getCountryCode()))
                    .userId(String.valueOf(random.nextInt(89999999)+10000000))
                    .build();

        customerRepository.save(customer);

        return toCustomerDto(customer);
    }

    public CustomerDto updateCustomer(String userId, UpdateCustomerRequest request){
        Customer customer = customerRepository.findByUserId(userId);
        
        if(authorizationCodeRequired) {
	        if(((request.getEmail()!=null && customer.getEmail()==null) ||
	                (request.getEmail()==null && customer.getEmail()!=null)||
	                (request.getEmail()!=null && !request.getEmail().equals(customer.getEmail())))
	                        && (request.getAuthCode()==null || !request.getAuthCode().equals(authCode))){
	            throw new CustomerUpdateException("Invalid Auth Code Provided!");
	        }
	
	        if(((request.getMobileNumber()!=null && customer.getMobileNumber()==null) ||
	                (request.getMobileNumber()==null && customer.getMobileNumber()!=null)||
	                (request.getMobileNumber()!=null && !request.getMobileNumber().equals(customer.getMobileNumber())))
	                        && (request.getAuthCode()==null || !request.getAuthCode().equals(authCode))){
	            throw new CustomerUpdateException("Invalid Auth Code Provided!");
	        }
        }

        customer.setSegmentation(request.getSegmentation());
        customer.setEmail(request.getEmail());
        customer.setMobileNumber(request.getMobileNumber());

        customerRepository.save(customer);

        return toCustomerDto(customer);
    }

    public void connectAccount(String userId, String accountNumber){
        Customer customer = customerRepository.findByUserId(userId);
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if(customer==null || account==null){
            throw new ConnectAccountException("Customer/Account pair not Found!");
        }

        CustomerAccount customerAccount = CustomerAccount.builder().customer(customer).account(account).build();

        customer.getCustomerAccounts().add(customerAccount);
        customerRepository.save(customer);
    }

    private CustomerDto toCustomerDto(Customer customer){
        return CustomerDto
                .builder()
                .userId(customer.getUserId())
                .name(customer.getFirstName() + " " + (customer.getMiddleName()==null?"":customer.getMiddleName()+" ")+ customer.getLastName())
                .sex(customer.getSex())
                .email(customer.getEmail())
                .idNumber(customer.getIdNumber())
                .mobileNumber(customer.getMobileNumber())
                .birthDate(customer.getBirthDate())
                .countryCode(customer.getCountry().getCode())
                .segmentation(customer.getSegmentation())
                .build();
    }

}
