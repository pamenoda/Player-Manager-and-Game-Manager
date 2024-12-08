package com.example.game_manager.controller;

import com.example.game_manager.dto.CreateGameDTO;
import com.example.game_manager.dto.GetGameDTO;
import com.example.game_manager.dto.UpdateGameDTO;
import com.example.game_manager.service.GameService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping
    public ResponseEntity<?> createGame(@RequestBody CreateGameDTO createGameDTO) {
        try {
            gameService.createGame(createGameDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created Successfully");
        } catch (IllegalArgumentException e) {
            // Handles logical errors (e.g., invalid host ID)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Handles unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable Long id) {
        try {
            GetGameDTO game = gameService.getGameById(id);
            return ResponseEntity.ok(game);
        } catch (IllegalArgumentException e) {
            // Handles logical errors (e.g., game not found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handles unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while fetching the game: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGame(@PathVariable Long id, @RequestBody UpdateGameDTO updateGameDTO) {
        try {
            gameService.updateGame(id, updateGameDTO);
            return ResponseEntity.ok("Game updated successfully.");
        } catch (IllegalArgumentException e) {
            // Handles logical errors (e.g., game not found or invalid data)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Handles unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while updating the game: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id) {
        try {
            gameService.deleteGame(id);
            return ResponseEntity.ok("Game deleted successfully.");
        } catch (IllegalArgumentException e) {
            // Handles logical errors (e.g., game not found)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Handles unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while deleting the game: " + e.getMessage());
        }
    }
}
