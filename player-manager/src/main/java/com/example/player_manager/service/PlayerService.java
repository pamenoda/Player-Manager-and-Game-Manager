package com.example.player_manager.service;

import com.example.player_manager.dao.IPlayerDAO;
import com.example.player_manager.dto.PlayerDTO;
import com.example.player_manager.entity.Player;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements IPlayerService{

    private final IPlayerDAO playerDAO;

    public PlayerService(IPlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }
    // create a player 
    @Override
    public Player createPlayer(Player player) {
        return playerDAO.save(player);
    }


    @Override
    public Player findById(Long id) {
        return playerDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    @Override
    public Player updatePlayer(Long id, PlayerDTO updatePlayerDTO) {
        Player existingPlayer = playerDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with ID: " + id));

        // Apply updates only for non-null or valid fields
        if (updatePlayerDTO.getUsername() != null) {
            existingPlayer.setUsername(updatePlayerDTO.getUsername());
        }
        if (updatePlayerDTO.getLevel() >= 0) {
            existingPlayer.setLevel(updatePlayerDTO.getLevel());
        }
        if (updatePlayerDTO.getTotalPoints() >= 0) {
            existingPlayer.setTotalPoints(updatePlayerDTO.getTotalPoints());
        }

        return playerDAO.save(existingPlayer);
    }

    @Override
    public void updatePlayerStats(Long id, int scoreToAdd) {
        Player player = playerDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with ID: " + id));

        // Calculer les nouveaux points
        int newTotalPoints = player.getTotalPoints() + scoreToAdd;

        // Calculer les niveaux supplémentaires gagnés
        int newLevel = newTotalPoints / 50; // Calculez directement le niveau en fonction des points totaux.
        player.setLevel(newLevel);

        // Mettre à jour les points totaux et le niveau
        player.setTotalPoints(newTotalPoints);
        player.setLevel(newLevel);

        // Sauvegarder les modifications
        playerDAO.save(player);
    }



    // get stat of player and info 
    @Override
    public PlayerDTO getPlayerInfo(Long id) {
        Player player = playerDAO.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Player not found with ID: " + id));
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setUsername(player.getUsername());
        playerDTO.setLevel(player.getLevel());
        playerDTO.setTotalPoints(player.getTotalPoints());
    return playerDTO;
  }

    // Supprimer un joueur
    @Override
     public void deletePlayer(Long id) {
        if (!playerDAO.existsById(id)) {
            throw new IllegalArgumentException("Player with ID " + id + " not found.");
        }
        playerDAO.deleteById(id);
    }
    
   


}
