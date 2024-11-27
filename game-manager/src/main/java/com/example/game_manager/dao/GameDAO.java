package com.example.game_manager.dao;

import com.example.game_manager.entity.Game;
import com.example.game_manager.repository.GameRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GameDAO implements IGameDAO {
    private final GameRepository gameRepository;

    public GameDAO(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return gameRepository.existsById(id);
    }
}
