package com.globetrotter.globetrotterchallenge.controller;

import com.globetrotter.globetrotterchallenge.entity.User;
import com.globetrotter.globetrotterchallenge.pojo.UserProfileResponse;
import com.globetrotter.globetrotterchallenge.pojo.UserRegistrationRequest;
import com.globetrotter.globetrotterchallenge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Operations related to user profiles")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Register new user",
            description = "Creates a new user profile with provided username"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    @PostMapping
    @CrossOrigin
    public ResponseEntity<UserProfileResponse> registerUser(
            @Parameter(description = "Username must be unique", required = true, example = "globetrotter123")
            @RequestBody UserRegistrationRequest request) {
        User user = userService.registerUser(request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserProfileResponse(
                        user.getUsername(),
                        user.getCorrectAnswers(),
                        user.getIncorrectAnswers()
                )
        );
    }

    @Operation(summary = "Get user profile", description = "Retrieve user's game statistics")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User profile found",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{username}")
    @CrossOrigin
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @Parameter(description = "Username to lookup", example = "globetrotter123")
            @PathVariable String username) {
        return ResponseEntity.ok(userService.getUserProfile(username));
    }
}