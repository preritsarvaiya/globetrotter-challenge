package com.globetrotter.globetrotterchallenge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globetrotter.globetrotterchallenge.entity.Destination;
import com.globetrotter.globetrotterchallenge.repository.DestinationRepository;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AiDataService {

    private final OpenAiService openAiService;
    private final ObjectMapper objectMapper;
    private final DestinationRepository destinationRepository;

    private static final List<String> SAMPLE_DESTINATIONS = List.of(
            "Paris, France", "Tokyo, Japan", "New York, USA", "Sydney, Australia",
            "Cairo, Egypt", "Rome, Italy", "Rio de Janeiro, Brazil", "Cape Town, South Africa",
            "Bangkok, Thailand", "Berlin, Germany", "Gujarat, India"
    );

    public AiDataService(@Value("${openai.api.key}") String apiKey, DestinationRepository destinationRepository) {
        this.openAiService = new OpenAiService(apiKey);
        this.objectMapper = new ObjectMapper();
        this.destinationRepository = destinationRepository;
    }

    /**
     * Generates and saves data for all destinations in a single API call.
     */
    public void generateAndSaveAllDestinations() {
        // Create a prompt that requests data for all destinations
        String prompt = buildPrompt();

        ChatMessage message = new ChatMessage("user", prompt);
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(message))
                .temperature(0.7)
                .maxTokens(4000)  // Ensure enough tokens for full response
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(request);
        String content = result.getChoices().get(0).getMessage().getContent();

        String jsonArray = extractJsonArray(content);
        saveDestinations(jsonArray);
    }

    /**
     * Constructs the prompt to fetch data for all destinations.
     */
    private String buildPrompt() {
        String destinationsList = SAMPLE_DESTINATIONS.stream()
                .map(name -> String.format("\"%s\"", name))
                .collect(Collectors.joining(", "));

        return String.format("""
                Generate a JSON array for a travel guessing game containing the following destinations: %s.
                Each destination should be formatted as:
                {
                  "city": "City name",
                  "country": "Country name",
                  "clues": ["Cryptic clue 1", "Cryptic clue 2"],
                  "funFacts": ["Interesting fact 1", "Interesting fact 2"],
                  "trivia": ["Trivia question 1", "Trivia question 2"]
                }
                """, destinationsList);
    }

    /**
     * Extracts JSON array from OpenAI's response.
     */
    private String extractJsonArray(String text) {
        Pattern pattern = Pattern.compile("\\[.*\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new RuntimeException("Failed to extract JSON array from response: " + text);
    }

    /**
     * Parses JSON and saves destinations in the database.
     */
    private void saveDestinations(String jsonArray) {
        try {
            List<Destination> destinations = objectMapper.readValue(
                    jsonArray, new TypeReference<>() {
                    }
            );
            destinationRepository.saveAll(destinations);
            System.out.println("Saved " + destinations.size() + " destinations.");
        } catch (Exception e) {
            throw new RuntimeException("Error parsing and saving JSON array", e);
        }
    }
}
