package com.bankofcyprus.demo.customer.h2.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bankofcyprus.demo.customer.h2.api.dto.AccountDto;
import com.bankofcyprus.demo.customer.h2.api.dto.AccountListDto;
import com.bankofcyprus.demo.customer.h2.model.Account;
import com.bankofcyprus.demo.customer.h2.repository.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    AccountRepository accountRepository;

    public AccountDto findAccount(String accountNumber){
        return toAccountDto(accountRepository.findByAccountNumber(accountNumber));
    }

    public AccountListDto findAllAccounts(){
        return AccountListDto.builder().accounts(accountRepository.findAll().stream()
                .map(account -> this.toAccountDto(account)).collect(Collectors.toList())).build();
    }

    private AccountDto toAccountDto(Account account){
        return AccountDto
                .builder()
                .accountType(account.getAccountType())
                .accountNumber(account.getAccountNumber())
                .availableBalance(account.getAvailableBalance())
                .currentBalance(account.getCurrentBalance())
                .build();
    }

}
