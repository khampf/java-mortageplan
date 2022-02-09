package com.example.mortageplan.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ProspectServiceTestConfiguration {
    @Bean
    @Primary
    public ProspectService productService() {
        return Mockito.mock(ProspectService.class);
    }
}