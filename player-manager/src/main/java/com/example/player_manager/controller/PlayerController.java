package com.example.player_manager.controller;

import com.example.player_manager.dto.PlayerDTO;

import com.example.player_manager.entity.Player;
import com.example.player_manager.service.PlayerService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // create player
    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        try {
            playerService.createPlayer(player);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating player: " + e.getMessage());
        }
    }

    // Update player's level and score
    @PostMapping("/{id}/update-stats")
    public ResponseEntity<?> updatePlayerStats(@PathVariable Long id, @RequestBody Map<String, Object> stats) {
        try {
            // fetch the score 
            if (!stats.containsKey("scoreToAdd")) {
                return ResponseEntity.badRequest().body("Missing required field: scoreToAdd");
            }

            int scoreToAdd = (int) stats.get("scoreToAdd");

            // call the function to add score 
            playerService.updatePlayerStats(id, scoreToAdd);
            return ResponseEntity.ok("Player stats updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while updating player stats: " + e.getMessage());
        }
    }

    // Reset data of a player username score level except email
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO updatePlayerDTO) {
        try {
            playerService.updatePlayer(id, updatePlayerDTO);
            return ResponseEntity.ok("Updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating player: " + e.getMessage());
        }
    }

    


    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable Long id) {
        try {
            // Appel au service pour obtenir les infos du joueur
            PlayerDTO playerDTO = playerService.getPlayerInfo(id);
            return ResponseEntity.ok(playerDTO); // Retourner 200 OK avec les infos du joueur
        } catch (IllegalArgumentException e) {
            // Le joueur n'existe pas
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Erreur inattendue
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    // delete a player
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        try {
            playerService.deletePlayer(id);
            return ResponseEntity.ok("Player deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting player: " + e.getMessage());
        }
    }
}
