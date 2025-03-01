package com.globetrotter.globetrotterchallenge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String country;

    @ElementCollection
    @CollectionTable(name = "destination_clues", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "clue")
    private List<String> clues = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "destination_fun_facts", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "fun_fact")
    private List<String> funFact = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "destination_trivia", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "trivia")
    private List<String> trivia = new ArrayList<>();
}