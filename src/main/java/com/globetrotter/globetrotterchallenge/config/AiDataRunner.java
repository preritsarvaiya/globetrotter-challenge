package com.globetrotter.globetrotterchallenge.config;

import com.globetrotter.globetrotterchallenge.service.AiDataService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiDataRunner {

    @Bean
    public ApplicationRunner loadDataOnStartup(AiDataService aiDataService) {
        return args -> {
            // Generate data for each destination with AI
            aiDataService.generateAndSaveAllDestinations();
        };
    }

}
