package com.globetrotter.globetrotterchallenge.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "User profile response containing game statistics")
public class UserProfileResponse {
    @Schema(description = "Unique username", example = "explorer")
    private String username;
    @Schema(description = "Total correct answers", example = "5")
    private int correctAnswers;
    @Schema(description = "Total incorrect answers", example = "2")
    private int incorrectAnswers;
}
