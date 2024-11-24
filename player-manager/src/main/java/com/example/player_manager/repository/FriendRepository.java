package com.example.player_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.player_manager.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("SELECT COUNT(f) > 0 FROM Friend f WHERE f.playerId = :playerId AND f.friendId = :friendId")
    boolean existsByPlayerIdAndFriendId(@Param("playerId") Long playerId, @Param("friendId") Long friendId);

    @Query("SELECT f.friendId FROM Friend f WHERE f.playerId = :playerId")
    List<Long> findFriendIdsByPlayerId(@Param("playerId") Long playerId);

    void deleteByPlayerIdAndFriendId(Long playerId, Long friendId);
}
