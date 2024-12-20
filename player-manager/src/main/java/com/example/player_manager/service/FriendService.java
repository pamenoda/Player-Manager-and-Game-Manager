package com.example.player_manager.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.player_manager.dao.IFriendDAO;
import com.example.player_manager.dto.FriendInfoDTO;
import com.example.player_manager.entity.Friend;
import com.example.player_manager.repository.FriendRepository;
import com.example.player_manager.repository.PlayerRepository;

import jakarta.transaction.Transactional;

@Service
public class FriendService implements IFriendService {

    private final FriendRepository friendRepository;
    private final PlayerRepository playerRepository;
    private final IFriendDAO friendDAO;

    public FriendService(FriendRepository friendRepository, PlayerRepository playerRepository,IFriendDAO friendDAO) {
        this.friendRepository = friendRepository;
        this.playerRepository = playerRepository;
        this.friendDAO = friendDAO;
    }

    // add a friend
    @Override
    public void addFriend(Long playerId, Long friendId) {
        if (playerRepository.existsById(playerId) && playerRepository.existsById(friendId)) {
             // Vérification si la relation existe déjà
            if (friendDAO.friendExists(playerId, friendId)) {
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
    @Override
    public List<FriendInfoDTO> getFriends(Long playerId) {
        return friendDAO.findFriendsByPlayerId(playerId);
    }

    // delete friends 
    @Override
    @Transactional
    public void removeFriend(Long playerId, Long friendId) {
        if (playerRepository.existsById(friendId) && playerRepository.existsById(playerId)) {
            friendDAO.deleteByPlayerIdAndFriendId(playerId, friendId);
        }
        else{
            throw new IllegalArgumentException("Invalid player or friend ID.");
        }
    }
}
