package com.example.game_manager.dao;

import com.example.game_manager.entity.Game;

import java.util.Optional;

public interface IGameDAO {
    Game save(Game game);

    Optional<Game> findById(Long id);

    void deleteById(Long id);
    
    boolean existsById(long id);
}
