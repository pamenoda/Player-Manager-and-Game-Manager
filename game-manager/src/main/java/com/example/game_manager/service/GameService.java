package com.example.game_manager.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.example.game_manager.dao.GameDAO;
import com.example.game_manager.dao.IGameDAO;
import com.example.game_manager.dto.CreateGameDTO;
import com.example.game_manager.dto.GetGameDTO;
import com.example.game_manager.dto.UpdateGameDTO;
import com.example.game_manager.entity.Game;
import com.example.game_manager.entity.GameType;

@Service
public class GameService {

    private final IGameDAO gameDAO;

    public GameService(IGameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    // Create a game
    public Game createGame(CreateGameDTO createGameDTO) {
        boolean isValidGameType = Arrays.stream(GameType.values())
                                    .anyMatch(type -> type.name().equals(createGameDTO.getGameType().toString()));
        if (!isValidGameType) 
        {
            throw new IllegalArgumentException("Invalid gameType. Allowed values are: " 
            + Arrays.toString(GameType.values()));
        }                          
        Game game = new Game();
        game.setDate(createGameDTO.getDate());
        game.setGameType(createGameDTO.getGameType());
        game.setMaxScore(createGameDTO.getMaxScore());
        game.setHostId(createGameDTO.getHostId());
        return gameDAO.save(game);
    }

    // Get a game by ID
    public GetGameDTO getGameById(Long id) {
        Game game = gameDAO.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Game with ID " + id + " not found."));
        GetGameDTO gameProtected = new GetGameDTO();
        gameProtected.setDate(game.getDate());
        gameProtected.setGameType(game.getGameType());
        gameProtected.setMaxScore(game.getMaxScore());
        return gameProtected;
    }


    // Update a game
    public Game updateGame(Long id, UpdateGameDTO updateGameDTO) {
        // Fetch the game
        Game existingGame = gameDAO.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Game with ID " + id + " not found."));

        // Update fields
        if (updateGameDTO.getGameType() != null) {
            existingGame.setGameType(updateGameDTO.getGameType());
        }
        if (updateGameDTO.getMaxScore() > 0) { // Assuming maxScore must be greater than 0
            existingGame.setMaxScore(updateGameDTO.getMaxScore());
        }
        // Save the updated game
        return gameDAO.save(existingGame);
    }

    // Delete a game
    public void deleteGame(Long id) {
        if (!gameDAO.existsById(id)) {
            throw new IllegalArgumentException("Game with ID " + id + " not found.");
        }
        gameDAO.deleteById(id);
    }
}
