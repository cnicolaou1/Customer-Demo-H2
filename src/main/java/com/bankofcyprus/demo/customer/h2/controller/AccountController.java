package com.bankofcyprus.demo.customer.h2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankofcyprus.demo.customer.h2.api.dto.AccountDto;
import com.bankofcyprus.demo.customer.h2.api.dto.AccountListDto;
import com.bankofcyprus.demo.customer.h2.service.AccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/accounts")
@AllArgsConstructor
public class AccountController
{
    AccountService accountService;

    @GetMapping("/{accountNumber}")
    public AccountDto getAccount(@PathVariable String accountNumber){
        return accountService.findAccount(accountNumber);
    }

    @GetMapping
    public AccountListDto getAccounts(){
        return accountService.findAllAccounts();
    }
}
