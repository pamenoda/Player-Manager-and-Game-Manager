package com.example.player_manager.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.player_manager.dto.FriendInfoDTO;
import com.example.player_manager.entity.Friend;
import com.example.player_manager.repository.FriendRepository;
import com.example.player_manager.repository.PlayerRepository;

import jakarta.transaction.Transactional;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final PlayerRepository playerRepository;

    public FriendService(FriendRepository friendRepository, PlayerRepository playerRepository) {
        this.friendRepository = friendRepository;
        this.playerRepository = playerRepository;
    }

    // add a friend
    public void addFriend(Long playerId, Long friendId) {
        if (playerRepository.existsById(playerId) && playerRepository.existsById(friendId)) {
             // Vérification si la relation existe déjà
            if (friendRepository.existsByPlayerIdAndFriendId(playerId, friendId)) {
                throw new IllegalArgumentException("Friendship already exists.");
            }
            else{
                Friend friend = new Friend();
                friend.setPlayerId(playerId);
                friend.setFriendId(friendId);
                friendRepository.save(friend);
            }    
        } else {
            throw new IllegalArgumentException("Invalid player or friend ID.");
        }
    }

    // get friends 
    public List<FriendInfoDTO> getFriends(Long playerId) {
    List<Long> friendIds = friendRepository.findFriendIdsByPlayerId(playerId);
    return playerRepository.findAllById(friendIds).stream()
            .map(player -> new FriendInfoDTO(
                    player.getUsername(),
                    player.getLevel(),
                    player.getTotalPoints()))
            .collect(Collectors.toList());
}

    @Transactional
    // delete friends 
    public void removeFriend(Long playerId, Long friendId) {
        friendRepository.deleteByPlayerIdAndFriendId(playerId, friendId);
    }
}
