package com.example.player_manager.dao;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.player_manager.entity.Player;
import com.example.player_manager.repository.PlayerRepository;

@Repository
public class PlayerDAO implements IPlayerDAO {
     private final PlayerRepository playerRepository;

    public PlayerDAO(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return playerRepository.existsById(id);
    }
}
