package com.example.game_manager.dao;

import com.example.game_manager.entity.Participation;
import com.example.game_manager.repository.ParticipationRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ParticipationDAO implements IParticipationDAO {

    private final ParticipationRepository participationRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public ParticipationDAO(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    @Override
    public Participation save(Participation participation) {
        return participationRepository.save(participation);
    }

    @Override
    public Optional<Participation> findById(Long id) {
        return participationRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        participationRepository.deleteById(id);
    }

    @Override
    public boolean existsByGameIdAndPlayerId(Long gameId, Long playerId) {
        String query = "SELECT COUNT(p) FROM Participation p WHERE p.gameId = :gameId AND p.playerId = :playerId";
        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
        typedQuery.setParameter("gameId", gameId);
        typedQuery.setParameter("playerId", playerId);
        Long count = typedQuery.getSingleResult();
        return count > 0;
    }
}
