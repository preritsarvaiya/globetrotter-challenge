package com.globetrotter.globetrotterchallenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private String username;
    private int correctAnswers;
    private int incorrectAnswers;
    private Long currentDestinationId;

    // Getters and setters
}