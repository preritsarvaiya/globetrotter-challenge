package com.globetrotter.globetrotterchallenge.service;

import com.globetrotter.globetrotterchallenge.entity.Destination;
import com.globetrotter.globetrotterchallenge.entity.User;
import com.globetrotter.globetrotterchallenge.pojo.DestinationDTO;
import com.globetrotter.globetrotterchallenge.pojo.GuessResult;
import com.globetrotter.globetrotterchallenge.repository.DestinationRepository;
import com.globetrotter.globetrotterchallenge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;

    public GameService(DestinationRepository destinationRepository, UserRepository userRepository) {
        this.destinationRepository = destinationRepository;
        this.userRepository = userRepository;
    }

    // Get Random Destination
    public DestinationDTO getRandomDestination(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Destination destination = destinationRepository.findRandom();
        if (destination == null) {
            throw new IllegalStateException("No destinations available in database");
        }
        user.setCurrentDestinationId(destination.getId());
        userRepository.save(user);

        List<String> clues = getRandomClues(destination.getClues());
        List<String> possibleAnswers = getPossibleAnswers(destination);

        return new DestinationDTO(clues, possibleAnswers);
    }

    // Submit the User's Guess
    public GuessResult submitGuess(String username, String guessedCity) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Destination destination = destinationRepository.findById(user.getCurrentDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        boolean isCorrect = guessedCity.equalsIgnoreCase(destination.getCity());
        updateUserScore(user, isCorrect);

        String funFact = isCorrect ? getRandomElement(destination.getFunFact()) : null;

        return new GuessResult(
                isCorrect,
                funFact,
                user.getCorrectAnswers(),
                user.getIncorrectAnswers()
        );
    }

    private List<String> getRandomClues(List<String> clues) {
        Collections.shuffle(clues);
        return clues.subList(0, Math.min(2, clues.size()));
    }

    private List<String> getPossibleAnswers(Destination correctDestination) {
        List<Destination> otherDestinations = destinationRepository.findRandomExcluding(
                correctDestination.getId(), 3
        );

        List<String> answers = otherDestinations.stream()
                .map(Destination::getCity)
                .collect(Collectors.toList());

        answers.add(correctDestination.getCity());
        Collections.shuffle(answers);
        return answers;
    }

    private void updateUserScore(User user, boolean correct) {
        if (correct) {
            user.setCorrectAnswers(user.getCorrectAnswers() + 1);
        } else {
            user.setIncorrectAnswers(user.getIncorrectAnswers() + 1);
        }
        userRepository.save(user);
    }

    private String getRandomElement(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}