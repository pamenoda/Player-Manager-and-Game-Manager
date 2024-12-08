package com.example.player_manager.dao;

import com.example.player_manager.entity.Player;
import java.util.Optional;

public interface IPlayerDAO {
    Player save(Player player); // save a player
    Optional<Player> findById(Long id); // search a player 
    void deleteById(Long id); // Supprimer un joueur par ID
    boolean existsById(Long id); // Vérifier l'existence d'un joueur
}
