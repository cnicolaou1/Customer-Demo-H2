package com.bankofcyprus.demo.customer.h2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfigBean {
    private String authCode;

    private Boolean authCodeRequired;

	public SecurityConfigBean(@Value("${customerDemo.authCode:1234}") String authCode ,
							  @Value("${customerDemo.authorizationCodeRequired:true}") Boolean authCodeRequired) {
		this.authCode=authCode;
		this.authCodeRequired=authCodeRequired;
	}
	
	@Bean
	public String authCode() {
		return authCode;
	}
	
	@Bean
	public boolean authorizationCodeRequired() {
		return authCodeRequired;
	}

}
