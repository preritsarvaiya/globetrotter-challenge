package com.globetrotter.globetrotterchallenge.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI globetrotterOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Globetrotter API")
                        .version("1.0")
                        .description("API for the Globetrotter geography guessing game")
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}