package com.globetrotter.globetrotterchallenge.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DestinationDTO {
    private List<String> clues;
    private List<String> possibleAnswers;
}
