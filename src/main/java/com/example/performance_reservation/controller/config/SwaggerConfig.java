package com.example.performance_reservation.controller.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                   .components(new Components())
                       .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                   .title("공연 예약 서비스")
                   .version("0.0.1");
    }
}
