package com.example.player_manager.service;

import com.example.player_manager.dto.PlayerDTO;
import com.example.player_manager.entity.Player;

public interface IPlayerService {
    Player createPlayer(Player player);
    Player findById(Long id);
    Player updatePlayer(Long id, PlayerDTO updatedPlayer);
    PlayerDTO getPlayerInfo(Long id);
    void deletePlayer(Long id);
    void updatePlayerStats(Long id, int newScore);
    
} 