package com.example.player_manager.service;

import com.example.player_manager.dto.PlayerDTO;
import com.example.player_manager.entity.Player;
import com.example.player_manager.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // create a player 
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player findById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    // update a player
    public Player updatePlayer(Long id, Player updatedPlayer) {
        return playerRepository.save(updatedPlayer);
    }

    // get stat of player and info 
    public PlayerDTO getPlayerInfo(Long id) {
        Player player = playerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Player not found with ID: " + id));
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setUsername(player.getUsername());
        playerDTO.setLevel(player.getLevel());
        playerDTO.setTotalPoints(player.getTotalPoints());
    return playerDTO;
}

    // Supprimer un joueur
     public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
    
   


}
