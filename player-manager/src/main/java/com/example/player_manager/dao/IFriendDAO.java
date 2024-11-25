package com.example.player_manager.dao;

import java.util.List;

import com.example.player_manager.dto.FriendInfoDTO;

public interface IFriendDAO {
    boolean friendExists(Long playerId, Long friendId);
    List<FriendInfoDTO> findFriendsByPlayerId(Long playerId);
    void deleteByPlayerIdAndFriendId(Long playerId, Long friendId);
}
