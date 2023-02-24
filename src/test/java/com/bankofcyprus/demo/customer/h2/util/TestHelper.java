package com.bankofcyprus.demo.customer.h2.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestHelper {

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule())
                                    .writeValueAsString(obj);
    }
}
