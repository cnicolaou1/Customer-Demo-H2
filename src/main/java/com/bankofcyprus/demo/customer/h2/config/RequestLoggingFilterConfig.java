package com.bankofcyprus.demo.customer.h2.config;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter() {
            @Override
            public void beforeRequest(HttpServletRequest request, String message) {
                request.setAttribute("xxx-time",new Date().getTime());
                logger.info(message);
            }
            @Override
            public void afterRequest(HttpServletRequest request, String message) {
                long reqTime = Long.valueOf(String.valueOf(request.getAttribute("xxx-time")));
                long respTime = new Date().getTime();

                long responseTime = respTime - reqTime;

                logger.info(message.replaceFirst("xxx", String.valueOf(responseTime)).replaceFirst(",.*]]","]"));
            }
        };

        filter.setIncludeQueryString(true);
        filter.setIncludePayload(false);
        filter.setIncludeHeaders(true);
        filter.setBeforeMessagePrefix("Received Request: ");

        filter.setAfterMessagePrefix("Response Time: xxx ms [");

        return filter;
    }
}