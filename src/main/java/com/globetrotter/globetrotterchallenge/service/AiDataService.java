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
//        String prompt = buildPrompt();
//
//        ChatMessage message = new ChatMessage("user", prompt);
//        ChatCompletionRequest request = ChatCompletionRequest.builder()
//                .model("gpt-3.5-turbo")
//                .messages(List.of(message))
//                .temperature(0.7)
//                .maxTokens(4000)  // Ensure enough tokens for full response
//                .build();
//
//        ChatCompletionResult result = openAiService.createChatCompletion(request);
//        String content = result.getChoices().get(0).getMessage().getContent();

        String content = """
                [
                  {
                    "city": "Paris",
                    "country": "France",
                    "clues": ["The Eiffel Tower is a famous landmark in this city", "The Louvre is home to the Mona Lisa painting"],
                    "funFacts": ["Paris is also known as the 'City of Light'", "The city has over 1,800 monuments and 173 museums"],
                    "trivia": ["What river flows through Paris?", "Which famous avenue in Paris is known for its luxury shops?"]
                  },
                  {
                    "city": "Tokyo",
                    "country": "Japan",
                    "clues": ["This city hosted the 2020 Summer Olympics", "The Imperial Palace is located in this city"],
                    "funFacts": ["Tokyo is the most populous city in Japan", "The city is known for its high-tech gadgets and futuristic architecture"],
                    "trivia": ["What is the name of the famous fish market in Tokyo?", "Which traditional Japanese theater form originated in Tokyo?"]
                  },
                  {
                    "city": "New York",
                    "country": "USA",
                    "clues": ["This city is home to the Statue of Liberty", "Times Square is a popular tourist attraction in this city"],
                    "funFacts": ["New York City is made up of 5 boroughs", "The city is known as the 'Big Apple'"],
                    "trivia": ["What river flows through New York City?", "Which iconic building in New York City is known for its art deco architecture?"]
                  },
                  {
                    "city": "Sydney",
                    "country": "Australia",
                    "clues": ["The Sydney Opera House is a famous landmark in this city", "The city is known for its beautiful beaches"],
                    "funFacts": ["Sydney is the largest city in Australia", "The city hosted the 2000 Summer Olympics"],
                    "trivia": ["What famous harbor is located in Sydney?", "Which famous bridge in Sydney is known for its arch design?"]
                  },
                  {
                    "city": "Cairo",
                    "country": "Egypt",
                    "clues": ["The Great Pyramid of Giza is located near this city", "The Sphinx is a famous monument in this city"],
                    "funFacts": ["Cairo is the capital of Egypt", "The city is known for its ancient history and archaeological sites"],
                    "trivia": ["What river flows through Cairo?", "Which museum in Cairo is home to a vast collection of ancient artifacts?"]
                  },
                  {
                    "city": "Rome",
                    "country": "Italy",
                    "clues": ["The Colosseum is located in this city", "The Vatican City is an independent city-state within this city"],
                    "funFacts": ["Rome is the capital of Italy", "The city is known for its rich history and ancient ruins"],
                    "trivia": ["What famous fountain is located in Rome?", "Which famous artist painted the ceiling of the Sistine Chapel in Rome?"]
                  },
                  {
                    "city": "Rio de Janeiro",
                    "country": "Brazil",
                    "clues": ["This city is known for its Carnival festival", "The Christ the Redeemer statue overlooks this city"],
                    "funFacts": ["Rio de Janeiro is the second-largest city in Brazil", "The city hosted the 2016 Summer Olympics"],
                    "trivia": ["What famous beach is located in Rio de Janeiro?", "Which famous mountain in Rio de Janeiro is known for its distinctive shape?"]
                  },
                  {
                    "city": "Cape Town",
                    "country": "South Africa",
                    "clues": ["Table Mountain overlooks this city", "The city is known for its diverse wildlife and natural beauty"],
                    "funFacts": ["Cape Town is the legislative capital of South Africa", "The city is one of the most multicultural cities in the world"],
                    "trivia": ["What famous cape is located near Cape Town?", "Which famous prison on an island near Cape Town housed political prisoners during apartheid?"]
                  },
                  {
                    "city": "Bangkok",
                    "country": "Thailand",
                    "clues": ["The Grand Palace is located in this city", "The city is known for its vibrant street food culture"],
                    "funFacts": ["Bangkok is the capital and largest city of Thailand", "The city is known for its ornate temples and bustling markets"],
                    "trivia": ["What famous river flows through Bangkok?", "Which famous reclining Buddha statue is located in Bangkok?"]
                  },
                  {
                    "city": "Berlin",
                    "country": "Germany",
                    "clues": ["The Berlin Wall used to divide this city", "The Brandenburg Gate is a famous landmark in this city"],
                    "funFacts": ["Berlin is the capital and largest city of Germany", "The city is known for its arts and culture scene"],
                    "trivia": ["What river flows through Berlin?", "Which famous museum in Berlin is home to the bust of Nefertiti?"]
                  },
                  {
                    "city": "Gujarat",
                    "country": "India",
                    "clues": ["This city is known for its vibrant textiles and handicrafts", "The city is home to many ancient temples and historical sites"],
                    "funFacts": ["Gujarat is a state in western India", "The city is known for its delicious cuisine and colorful festivals"],
                    "trivia": ["What famous river flows through Gujarat?", "Which famous salt desert is located in Gujarat?"]
                  }
                ]
                """;
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
