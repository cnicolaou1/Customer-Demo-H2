package com.bankofcyprus.demo.customer.h2.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofcyprus.demo.customer.h2.api.dto.AccountDto;
import com.bankofcyprus.demo.customer.h2.model.Account;
import com.bankofcyprus.demo.customer.h2.repository.AccountRepository;

@Service
@AllArgsConstructor
public class AccountService {
    AccountRepository accountRepository;

    public AccountDto findAccount(String accountNumber){
        return toAccountDto(accountRepository.findByAccountNumber(accountNumber));
    }

    public List<AccountDto> findAllAccounts(){
        return accountRepository.findAll().stream()
                .map(account -> this.toAccountDto(account)).collect(Collectors.toList());
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
