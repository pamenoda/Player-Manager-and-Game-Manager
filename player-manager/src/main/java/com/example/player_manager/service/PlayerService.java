package com.example.player_manager.service;

import com.example.player_manager.entity.Player;
import com.example.player_manager.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // créer un joueur 
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player findById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    // Modifier un joueur
    public Player updatePlayer(Long id, Player updatedPlayer) {
        return playerRepository.save(updatedPlayer);
    }

    // Supprimer un joueur
     public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
    
   


}
