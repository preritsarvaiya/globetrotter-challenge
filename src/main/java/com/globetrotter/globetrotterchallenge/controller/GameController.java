package com.globetrotter.globetrotterchallenge.controller;

import com.globetrotter.globetrotterchallenge.pojo.DestinationDTO;
import com.globetrotter.globetrotterchallenge.pojo.GuessRequest;
import com.globetrotter.globetrotterchallenge.pojo.GuessResult;
import com.globetrotter.globetrotterchallenge.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@Tag(name = "Game API")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(
            summary = "Get random destination",
            description = "Retrieves a random destination with 1-2 clues and possible answers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game data retrieved",
                    content = @Content(schema = @Schema(implementation = DestinationDTO.class))),
            @ApiResponse(responseCode = "404", description = "No destinations available")
    })
    @GetMapping("/random")
    public ResponseEntity<DestinationDTO> getRandomDestination(
            @Parameter(description = "Current user's username", required = true, example = "adventure_seeker")
            @RequestParam String username) {
        return ResponseEntity.ok(gameService.getRandomDestination(username));
    }

    @Operation(
            summary = "Submit destination guess",
            description = "Validate user's guess and return game result"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Guess processed",
                    content = @Content(schema = @Schema(implementation = GuessResult.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request format")
    })
    @PostMapping("/guess")
    public ResponseEntity<GuessResult> submitGuess(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Guess submission payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = GuessRequest.class)))
            @RequestBody GuessRequest request) {
        return ResponseEntity.ok(gameService.submitGuess(
                request.getUsername(),
                request.getGuessedCity()
        ));
    }
}