# Globetrotter Challenge

**Globetrotter Challenge** is a full-stack web application that challenges users to guess famous travel destinations based on cryptic clues. Upon guessing correctly, users unlock fun facts, trivia, and surprises about the location.

## Table of Contents
1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Setup and Installation](#setup-and-installation)
4. [API Endpoints](#api-endpoints)
5. [License](#license)

## Features
- **Random Destination:** Retrieve a random destination using OpenAI with clues, fun facts, and trivia.
- **Answer Validation:** Validate user guesses and provide instant feedback.
- **User Registration & Scoring:** Register users and track their correct/incorrect answers.
- **Challenge a Friend:** Generate unique invitation links that display the inviter’s score.
- **Interactive API Documentation:** Swagger-powered API docs for easy endpoint testing.

## Tech Stack
- **Backend:** Java, Spring Boot, Spring Data JPA
- **Database:** MySQL
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Build Tool:** Maven

## Setup and Installation
1. **Clone the Repository**
   ```sh
   git clone https://github.com/yourusername/GlobetrotterChallenge.git
   cd GlobetrotterChallenge 
2. **Build the Project**
    ```mvn clean package```
3. **Run the Application**
    ```mvn spring-boot:run```
4. **Access the Application**
    1. Random Destination API: http://localhost:8080/
   2. Swagger UI: http://localhost:8080/swagger-ui/index.html

## API Endpoints
### Destinations
1. GET /destinations/random
   Retrieves a random destination with clues, fun facts, and trivia.
2. POST /destinations/answer
   Validates the user's answer for a given destination.
   Parameters:
    1. destinationId (ID of the destination)
   2. answer (User’s guess)

### Users
1. POST /user/register
   Registers a new user.
   Parameters:
   1. username (Unique username)
2. GET /user/{username}/score
   Retrieves the score for the specified user.
3. GET /user/invite
   Generates an invitation link for the user, including a token and the user's current score.
   Query Parameter:
    1. username (Inviter’s username)

## License
### This project is licensed under the MIT License.

## Enjoy playing Globetrotter Challenge and test your travel knowledge! 🌍✨
