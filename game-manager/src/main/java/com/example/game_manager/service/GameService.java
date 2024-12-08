package com.example.game_manager.service;

import java.time.LocalDate;
import java.util.Arrays;


import org.springframework.stereotype.Service;
import com.example.game_manager.dao.IGameDAO;
import com.example.game_manager.dto.CreateGameDTO;
import com.example.game_manager.dto.GetGameDTO;
import com.example.game_manager.dto.UpdateGameDTO;
import com.example.game_manager.entity.Game;
import com.example.game_manager.entity.GameType;


@Service
public class GameService implements IGameService {

    private final IGameDAO gameDAO;
    private final IExternalApiService externalApiService;

    public GameService(IGameDAO gameDAO, IExternalApiService externalApiService) {
        this.gameDAO = gameDAO;
        this.externalApiService = externalApiService;
    }

    // Create a game
    @Override
    public Game createGame(CreateGameDTO createGameDTO) {
        if (createGameDTO.getGameType() == null) {
            throw new IllegalArgumentException("GameType cannot be null. Allowed values are: " 
                    + Arrays.toString(GameType.values()));
        }
        boolean isValidGameType = Arrays.stream(GameType.values())
                                    .anyMatch(type -> type.name().toUpperCase().equals(createGameDTO.getGameType().toString().toUpperCase()));
        if (!isValidGameType) 
        {
            throw new IllegalArgumentException("Invalid gameType. Allowed values are: " 
            + Arrays.toString(GameType.values()));
        }
        // check if the host exists
        boolean hostExists = externalApiService.checkIfPlayerExists(createGameDTO.getHostId());
        if (!hostExists) {
            throw new IllegalArgumentException("Host with ID " + createGameDTO.getHostId() + " does not exist.");
        }
        Game game = new Game(LocalDate.now(),createGameDTO.getGameType(),createGameDTO.getMaxScore(),createGameDTO.getHostId());
        return gameDAO.save(game);
    }

    
    // Get a game by ID
    @Override
    public GetGameDTO getGameById(Long id) {
        Game game = validateGameExists(id); 
        GetGameDTO gameProtected = new GetGameDTO(game.getDate(),game.getGameType(),game.getMaxScore());
        return gameProtected;
    }

    // Validate if a game exists by ID
    @Override
    public Game validateGameExists(Long gameId) {
        return gameDAO.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game with ID " + gameId + " does not exist."));
    }
    // Update a game
    @Override
    public Game updateGame(Long id, UpdateGameDTO updateGameDTO) {
        // Fetch the game
        Game existingGame = validateGameExists(id); // Reuse validation method

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
    @Override
    public void deleteGame(Long id) {
        validateGameExists(id); // Reuse validation method
        gameDAO.deleteById(id);
    }
}
