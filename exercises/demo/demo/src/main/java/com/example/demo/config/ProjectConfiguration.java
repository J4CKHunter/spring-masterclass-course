package com.example.demo.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProjectConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthenticationInterceptor(){
        return new BasicAuthRequestInterceptor("admin@eazyschool.com","admin");
    }

    @Bean
    RestTemplate restTemplate(){
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.basicAuthentication("admin@eazyschool.com", "admin").build();

        return restTemplate;
    }

    @Bean
    public WebClient webClient(){

        return WebClient.builder()
                .filter(ExchangeFilterFunctions.basicAuthentication("admin@eazyschool.com","admin"))
                .build();
    }

}
