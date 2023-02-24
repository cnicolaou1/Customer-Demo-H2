package com.bankofcyprus.demo.customer.h2.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bankofcyprus.demo.customer.h2.api.dto.AccountDto;
import com.bankofcyprus.demo.customer.h2.service.AccountService;

@WebMvcTest(AccountController.class)
class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

    @Test
    void getAccount() throws Exception {
        String accountNumber = "123456";
        String response = "{\"accountNumber\":\"123456\",\"accountType\":\"TYPE\",\"availableBalance\":0,\"currentBalance\":0}";
        when(service.findAccount(accountNumber)).thenReturn(this.mockAccountDto(accountNumber));
        this.mockMvc.perform(get("/v1/accounts/"+accountNumber)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(response)));
    }

    @Test
    void getAccounts() throws Exception {
        String accountNumber = "123456";
        List<AccountDto> accounts = new ArrayList<AccountDto>();
        accounts.add(this.mockAccountDto(accountNumber));

        String response = "[{\"accountNumber\":\"123456\",\"accountType\":\"TYPE\",\"availableBalance\":0,\"currentBalance\":0}]";
        when(service.findAllAccounts()).thenReturn(accounts);
        this.mockMvc.perform(get("/v1/accounts/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(response)));
    }

    private AccountDto mockAccountDto(String accountNumber){
        return AccountDto.builder()
                .accountNumber(accountNumber)
                .accountType("TYPE")
                .currentBalance(BigDecimal.ZERO)
                .availableBalance(BigDecimal.ZERO)
                .build();
    }
}
