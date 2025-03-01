//package com.globetrotter.globetrotterchallenge.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.globetrotter.globetrotterchallenge.entity.Destination;
//import com.globetrotter.globetrotterchallenge.repository.DestinationRepository;
//import com.theokanning.openai.OpenAiService;
//import com.theokanning.openai.completion.chat.ChatCompletionRequest;
//import com.theokanning.openai.completion.chat.ChatCompletionResult;
//import com.theokanning.openai.completion.chat.ChatMessage;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//public class AiDataService {
//
//    private final OpenAiService openAiService;
//    private final ObjectMapper objectMapper;
//    private final DestinationRepository destinationRepository;
//
//    public AiDataService(@Value("${openai.api.key}") String apiKey, DestinationRepository destinationRepository) {
//        this.openAiService = new OpenAiService(apiKey);
//        this.objectMapper = new ObjectMapper();
//        this.destinationRepository = destinationRepository;
//    }
//
//    /**
//     * Generates and saves destination data in the database.
//     *
//     * @param destinationName the name of the destination (e.g., "Paris, France")
//     */
//    public void generateAndSaveDestinationData(String destinationName) {
//        String prompt = String.format("""
//                Generate a JSON entry for a travel guessing game about "%s".
//                The JSON format:
//                {
//                  "city": "%s",
//                  "country": "country where %s is located",
//                  "clues": [2 cryptic clues],
//                  "funFacts": [2 interesting facts],
//                  "trivia": [2 trivia questions]
//                }
//                """, destinationName, destinationName, destinationName);
//
//        ChatMessage message = new ChatMessage("user", prompt);
//        ChatCompletionRequest request = ChatCompletionRequest.builder()
//                .model("gpt-3.5-turbo")
//                .messages(List.of(message))
//                .temperature(0.7)
//                .maxTokens(500)
//                .build();
//
//        ChatCompletionResult result = openAiService.createChatCompletion(request);
//        String content = result.getChoices().get(0).getMessage().getContent();
//
//        String json = extractJson(content);
//        try {
//            Destination destination = objectMapper.readValue(json, Destination.class);
//            destinationRepository.save(destination);
//            System.out.println("Saved destination: " + destination.getCity());
//        } catch (Exception e) {
//            throw new RuntimeException("Error parsing and saving JSON", e);
//        }
//    }
//
//    /**
//     * Extracts JSON from OpenAI's response.
//     */
//    private String extractJson(String text) {
//        Pattern pattern = Pattern.compile("\\{[\\s\\S]*\\}");
//        Matcher matcher = pattern.matcher(text);
//        if (matcher.find()) {
//            return matcher.group();
//        }
//        throw new RuntimeException("Failed to extract JSON from response: " + text);
//    }
//}
