package com.bankofcyprus.demo.customer.h2.controller;

import com.bankofcyprus.demo.customer.h2.api.dto.ConnectAccountRequest;
import com.bankofcyprus.demo.customer.h2.api.dto.CreateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.api.dto.CustomerDto;
import com.bankofcyprus.demo.customer.h2.api.dto.UpdateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/customers")
@AllArgsConstructor
public class CustomerController
{
    private CustomerService customerService;

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable String userId){
        return ResponseEntity.ok(customerService.findCustomer(userId));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers(){
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable String userId, @RequestBody UpdateCustomerRequest request){
        return ResponseEntity.ok(customerService.updateCustomer(userId, request));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> connectAccount(@PathVariable String userId, @RequestBody ConnectAccountRequest request){
        customerService.connectAccount(userId, request.getAccountNumber());

        return ResponseEntity.ok().build();
    }
}
