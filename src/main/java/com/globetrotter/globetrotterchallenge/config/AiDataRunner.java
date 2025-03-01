//package com.globetrotter.globetrotterchallenge.config;
//
//import com.globetrotter.globetrotterchallenge.service.AiDataService;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class AiDataRunner {
//
//    private static final List<String> SAMPLE_DESTINATIONS = List.of(
//            "Paris, France", "Tokyo, Japan", "New York, USA", "Sydney, Australia",
//            "Cairo, Egypt", "Rome, Italy", "Rio de Janeiro, Brazil", "Cape Town, South Africa",
//            "Bangkok, Thailand", "Berlin, Germany", "Gujarat, India"
//    );
//
//    @Bean
//    public ApplicationRunner loadDataOnStartup(AiDataService aiDataService) {
//        return args -> {
//            // Generate data for each destination in SAMPLE_DESTINATIONS
//            for (String destinationName : SAMPLE_DESTINATIONS) {
//                aiDataService.generateAndSaveDestinationData(destinationName);
//            }
//            System.out.println("Finished generating destinations from SAMPLE_DESTINATIONS.");
//        };
//    }
//
//}
