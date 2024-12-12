package com.example.player_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.player_manager.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByPlayerIdAndFriendId(@Param("playerId") Long playerId, @Param("friendId") Long friendId);

    List<Long> findFriendIdsByPlayerId(@Param("playerId") Long playerId);

    void deleteByPlayerIdAndFriendId(Long playerId, Long friendId);
}
