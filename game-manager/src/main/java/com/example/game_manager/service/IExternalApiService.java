package com.example.game_manager.service;

public interface IExternalApiService {
    void updatePlayerStats(Long playerId, int scoreToAdd);
    boolean checkIfPlayerExists(Long playerId);
}
