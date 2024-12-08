package com.example.game_manager.service;

import com.example.game_manager.dto.CreateGameDTO;
import com.example.game_manager.dto.GetGameDTO;
import com.example.game_manager.dto.UpdateGameDTO;
import com.example.game_manager.entity.Game;

public interface IGameService {

    // Create a game
    Game createGame(CreateGameDTO createGameDTO);

    // Get a game by ID
    GetGameDTO getGameById(Long id);

    // Validate if a game exists by ID
    Game validateGameExists(Long gameId);

    // Update a game
    Game updateGame(Long id, UpdateGameDTO updateGameDTO);

    // Delete a game
    void deleteGame(Long id);
}
