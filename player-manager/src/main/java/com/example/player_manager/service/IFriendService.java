package com.example.player_manager.service;

import java.util.List;

import com.example.player_manager.dto.FriendInfoDTO;

public interface IFriendService {
    void addFriend(Long playerId, Long friendId);
    List<FriendInfoDTO> getFriends(Long playerId);
    void removeFriend(Long playerId, Long friendId);
}
