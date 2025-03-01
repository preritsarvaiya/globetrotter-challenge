package com.globetrotter.globetrotterchallenge.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DestinationData {
    private String city;
    private String country;
    private List<String> clues;
    private List<String> funFacts;
    private List<String> trivia;
}
