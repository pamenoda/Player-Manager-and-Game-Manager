package com.example.game_manager.controller;

import com.example.game_manager.dto.CreateGameDTO;
import com.example.game_manager.dto.GetGameDTO;
import com.example.game_manager.dto.UpdateGameDTO;
import com.example.game_manager.entity.Game;
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

    // Endpoint to create game
    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody CreateGameDTO createGameDTO) {
        Game createdGame = gameService.createGame(createGameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);  
    }

    // Endpoint to get game info 
    @GetMapping("/{id}")
    public ResponseEntity<GetGameDTO> getGameById(@PathVariable Long id) {
        GetGameDTO game = gameService.getGameById(id);
        return ResponseEntity.ok(game); 
    }

    //EndPoint to update a game 
    @PutMapping("/{id}")
    public ResponseEntity<String> updateGame(@PathVariable Long id, @RequestBody UpdateGameDTO updateGameDTO) {
        gameService.updateGame(id, updateGameDTO);
        return ResponseEntity.ok("Game updated successfully.");
    }

    // Endpoint to delete a game 
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok("Game deleted successfully.");
    }
}
