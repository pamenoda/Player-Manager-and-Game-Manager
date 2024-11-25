package com.example.player_manager.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.example.player_manager.dto.FriendInfoDTO;

import java.util.List;

@Repository
public class FriendDAO implements IFriendDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean friendExists(Long playerId, Long friendId) {
        String query = "SELECT COUNT(f) > 0 FROM Friend f WHERE f.playerId = :playerId AND f.friendId = :friendId";
        return (boolean) entityManager.createQuery(query)
                .setParameter("playerId", playerId)
                .setParameter("friendId", friendId)
                .getSingleResult();
    }

     @Override
    public List<FriendInfoDTO> findFriendsByPlayerId(Long playerId) {
        String query = "SELECT new com.example.player_manager.dto.FriendInfoDTO(p.username, p.level, p.totalPoints) " +
                       "FROM Friend f JOIN Player p ON f.friendId = p.id " +
                       "WHERE f.playerId = :playerId";

        return entityManager.createQuery(query, FriendInfoDTO.class)
                .setParameter("playerId", playerId)
                .getResultList();
    }

    @Override
    public void deleteByPlayerIdAndFriendId(Long playerId, Long friendId) {
        String query = "DELETE FROM Friend f WHERE f.playerId = :playerId AND f.friendId = :friendId";
        entityManager.createQuery(query)
                .setParameter("playerId", playerId)
                .setParameter("friendId", friendId)
                .executeUpdate();
    }
}
