package com.example.game_manager.dao;

import com.example.game_manager.entity.Participation;

import java.util.Optional;

public interface IParticipationDAO {
    Participation save(Participation participation);

    Optional<Participation> findById(Long id);

    void deleteById(Long id);
    
    boolean existsByGameIdAndPlayerId(Long gameId, Long playerId);
}
