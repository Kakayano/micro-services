package org.example.productcompositeservice.configuration;

import org.example.productcompositeservice.controller.ProductCompositeIntegration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductCompositeConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
