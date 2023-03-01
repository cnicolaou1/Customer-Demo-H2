package com.bankofcyprus.demo.customer.h2.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankofcyprus.demo.customer.h2.api.dto.ConnectAccountRequest;
import com.bankofcyprus.demo.customer.h2.api.dto.CreateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.api.dto.CustomerDto;
import com.bankofcyprus.demo.customer.h2.api.dto.CustomerListDto;
import com.bankofcyprus.demo.customer.h2.api.dto.UpdateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.service.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/customers")
@AllArgsConstructor
public class CustomerController
{
    private CustomerService customerService;

    @GetMapping("/{userId}")
    public CustomerDto getCustomer(@PathVariable String userId){
        return customerService.findCustomer(userId);
    }

    @GetMapping
    public CustomerListDto getCustomers(){
        return customerService.findAllCustomers();
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return new ResponseEntity<CustomerDto>(customerService.createCustomer(request), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public CustomerDto updateCustomer(@PathVariable String userId, @RequestBody UpdateCustomerRequest request){
        return customerService.updateCustomer(userId, request);
    }

    @PostMapping("/{userId}")
    public void connectAccount(@PathVariable String userId, @RequestBody ConnectAccountRequest request){
        customerService.connectAccount(userId, request.getAccountNumber());
    }
}
