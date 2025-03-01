package com.globetrotter.globetrotterchallenge.service;

import com.globetrotter.globetrotterchallenge.entity.User;
import com.globetrotter.globetrotterchallenge.pojo.UserProfileResponse;
import com.globetrotter.globetrotterchallenge.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username) {
        if (userRepository.existsById(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        return userRepository.save(user);
    }

    public UserProfileResponse getUserProfile(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(
                user.getUsername(),
                user.getCorrectAnswers(),
                user.getIncorrectAnswers()
        );
    }
}