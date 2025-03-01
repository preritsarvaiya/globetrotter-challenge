package com.globetrotter.globetrotterchallenge.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuessRequest {
    private String username;
    private String guessedCity;
}
