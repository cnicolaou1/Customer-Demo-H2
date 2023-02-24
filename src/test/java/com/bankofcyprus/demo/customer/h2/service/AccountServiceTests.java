package com.bankofcyprus.demo.customer.h2.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bankofcyprus.demo.customer.h2.api.dto.AccountDto;
import com.bankofcyprus.demo.customer.h2.model.Account;
import com.bankofcyprus.demo.customer.h2.repository.AccountRepository;

@SpringBootTest(classes = {AccountService.class})
class AccountServiceTests {

	@Autowired
	AccountService service;
	
    @MockBean
    AccountRepository repository;

    @Test
    void findAccount(){
        String accountNumber = "123456";
        Account account = this.mockAccount(accountNumber);
        
        when(repository.findByAccountNumber(accountNumber)).thenReturn(account);
        
        AccountDto accountDto = service.findAccount(accountNumber);
        
        checkAccountDto(accountDto, account);
    }
    
    @Test
    void findAllAccount(){
        String accountNumber = "123456";
        Account account = this.mockAccount(accountNumber);
        
        when(repository.findAll()).thenReturn(Arrays.asList(account));
        
        List<AccountDto> accounts = service.findAllAccounts();
        
        Assertions.assertNotNull(accounts);
        Assertions.assertEquals(1, accounts.size());
        
        checkAccountDto(accounts.get(0), account);
    }
    
    void checkAccountDto(AccountDto accountDto, Account account) {
    	Assertions.assertNotNull(accountDto);
        Assertions.assertEquals(accountDto.getAccountNumber(),account.getAccountNumber());
        Assertions.assertEquals(accountDto.getAccountType(),account.getAccountType());
        Assertions.assertEquals(accountDto.getAvailableBalance(),account.getAvailableBalance());
        Assertions.assertEquals(accountDto.getCurrentBalance(),account.getCurrentBalance());
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
}
