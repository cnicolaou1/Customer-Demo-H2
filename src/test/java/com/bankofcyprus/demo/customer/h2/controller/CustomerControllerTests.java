package com.bankofcyprus.demo.customer.h2.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bankofcyprus.demo.customer.h2.api.dto.CreateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.api.dto.CustomerDto;
import com.bankofcyprus.demo.customer.h2.api.dto.UpdateCustomerRequest;
import com.bankofcyprus.demo.customer.h2.exception.CustomerUpdateException;
import com.bankofcyprus.demo.customer.h2.model.Segmentation;
import com.bankofcyprus.demo.customer.h2.model.Sex;
import com.bankofcyprus.demo.customer.h2.service.CustomerService;
import com.bankofcyprus.demo.customer.h2.util.TestHelper;

@WebMvcTest(CustomerController.class)
class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    private final static String NAME = "Test Test";
    private final static String FIRST_NAME = "Test";
    private final static String LAST_NAME = "Test";
    private final static String USER_ID = "123456";
    private final static String EMAIL = "test@test.com";
    private final static String EMAIL_NEW = "test.test@test.com";
    private final static String ID_NUMBER = "123456";
    private final static String COUNTRY_CODE = "CY";
    private final static String MOBILE_NUMBER = "123456";
    private final static String MOBILE_NUMBER_NEW = "99123456";
    private final static Sex SEX = Sex.FEMALE;
    private final static Segmentation SEGMENTATION = Segmentation.RETAIL;
    private final static Segmentation SEGMENTATION_NEW = Segmentation.CORPORATE;
    private final static LocalDate BIRTH_DATE = LocalDate.of(2000,01,01);
    private final static String AUTH_CODE = "1234";

    @Test
    void getCustomer() throws Exception {
//        String userId = "123456";
        String response = "{\"userId\":\""+USER_ID+"\""+
                            ",\"name\":\""+NAME+"\""+
                            ",\"sex\":\""+SEX.name()+"\""+
                            ",\"email\":\""+EMAIL+"\""+
                            ",\"mobileNumber\":\""+MOBILE_NUMBER+"\""+
                            ",\"idNumber\":\""+ID_NUMBER+"\""+
                            ",\"birthDate\":\""+BIRTH_DATE+"\""+
                            ",\"segmentation\":\""+SEGMENTATION.name()+"\""+
                            ",\"countryCode\":\""+COUNTRY_CODE+"\""+
                            "}";

//        {"userId":"123456","name":"Test Test","sex":"FEMALE","email":"test@test.com","mobileNumber":"123456","idNumber":"123456","birthDate":"2000-01-01","segmentation":"RETAIL","countryCode":"CY","customerAccounts":null}

        when(service.findCustomer(USER_ID)).thenReturn(mockCustomerDto());
        mockMvc.perform(get("/v1/customers/"+USER_ID)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(response)));
    }

    @Test
    void getCustomers() throws Exception {
//        String userId = "123456";
        List<CustomerDto> customerDtos = new ArrayList<CustomerDto>();
        customerDtos.add(mockCustomerDto());

        String response = "[{\"userId\":\""+USER_ID+"\""+
                ",\"name\":\""+NAME+"\""+
                ",\"sex\":\""+SEX.name()+"\""+
                ",\"email\":\""+EMAIL+"\""+
                ",\"mobileNumber\":\""+MOBILE_NUMBER+"\""+
                ",\"idNumber\":\""+ID_NUMBER+"\""+
                ",\"birthDate\":\""+BIRTH_DATE+"\""+
                ",\"segmentation\":\""+SEGMENTATION.name()+"\""+
                ",\"countryCode\":\""+COUNTRY_CODE+"\""+
                "}]";

        when(service.findAllCustomers()).thenReturn(customerDtos);
        mockMvc.perform(get("/v1/customers/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(response)));
    }

    @Test
    void createCustomer() throws Exception {
        CreateCustomerRequest customerRequest = mockCreateCustomerRequest();
        CustomerDto customerDto = mockCustomerDto();

        when(service.createCustomer(customerRequest)).thenReturn(customerDto);

        mockMvc.perform(post("/v1/customers/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestHelper.asJsonString(customerRequest)))
                        .andDo(print())
                        .andExpect(status().isOk())
                            .andExpect(content().string(equalTo(TestHelper.asJsonString(customerDto))));

        customerRequest.setLastName(null);

        mockMvc.perform(post("/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.asJsonString(customerRequest)))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
    }

    @Test
    void updateCustomer() throws Exception {
        UpdateCustomerRequest updateCustomerRequest = mockUpdateCustomerRequest();
        CustomerDto customerDto = mockCustomerDto();
        customerDto.setEmail(updateCustomerRequest.getEmail());
        customerDto.setMobileNumber(updateCustomerRequest.getMobileNumber());
        customerDto.setSegmentation(updateCustomerRequest.getSegmentation());

        when(service.updateCustomer(USER_ID, updateCustomerRequest)).thenReturn(customerDto);
        mockMvc.perform(put("/v1/customers/"+USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.asJsonString(updateCustomerRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(TestHelper.asJsonString(customerDto))));

        updateCustomerRequest.setAuthCode("1111");

        when(service.updateCustomer(USER_ID, updateCustomerRequest)).thenThrow(new CustomerUpdateException("Invalid Auth Code!"));
        mockMvc.perform(put("/v1/customers/"+USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.asJsonString(updateCustomerRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private CustomerDto mockCustomerDto(){
        return CustomerDto.builder()
                .userId(USER_ID)
                .segmentation(SEGMENTATION)
                .name(NAME)
                .email(EMAIL)
                .idNumber(ID_NUMBER)
                .countryCode(COUNTRY_CODE)
                .mobileNumber(MOBILE_NUMBER)
                .sex(SEX)
                .birthDate(BIRTH_DATE)
                .build();
    }

    private CreateCustomerRequest mockCreateCustomerRequest(){
        return CreateCustomerRequest.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .segmentation(SEGMENTATION)
                .email(EMAIL)
                .idNumber(ID_NUMBER)
                .countryCode(COUNTRY_CODE)
                .mobileNumber(MOBILE_NUMBER)
                .sex(SEX)
                .birthDate(BIRTH_DATE)
                .build();
    }

    private UpdateCustomerRequest mockUpdateCustomerRequest(){
        return UpdateCustomerRequest.builder()
                .segmentation(SEGMENTATION_NEW)
                .email(EMAIL_NEW)
                .mobileNumber(MOBILE_NUMBER_NEW)
                .authCode(AUTH_CODE)
                .build();
    }
}
