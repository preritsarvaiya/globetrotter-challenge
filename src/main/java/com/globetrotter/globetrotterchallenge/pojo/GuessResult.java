package com.globetrotter.globetrotterchallenge.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GuessResult {
    private boolean correct;
    private String funFact;
    private int correctAnswers;
    private int incorrectAnswers;
}
