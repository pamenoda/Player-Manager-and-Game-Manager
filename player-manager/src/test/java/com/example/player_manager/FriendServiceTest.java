package com.example.player_manager;

import com.example.player_manager.dao.IFriendDAO;
import com.example.player_manager.dto.FriendInfoDTO;
import com.example.player_manager.entity.Friend;
import com.example.player_manager.repository.FriendRepository;
import com.example.player_manager.repository.PlayerRepository;
import com.example.player_manager.service.FriendService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FriendServiceTest {

    @Mock
    private FriendRepository friendRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private IFriendDAO friendDAO;

    @InjectMocks
    private FriendService friendService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFriend_ShouldAddFriend_WhenValidIds() {
        // set up the expected value
        Long playerId = 1L;
        Long friendId = 2L;

        //  execute to get the  response
        when(playerRepository.existsById(playerId)).thenReturn(true);
        when(playerRepository.existsById(friendId)).thenReturn(true);
        when(friendDAO.friendExists(playerId, friendId)).thenReturn(false);

        friendService.addFriend(playerId, friendId);
        //check value with expected value 
        verify(friendRepository, times(1)).save(any(Friend.class));
    }

    @Test
    void addFriend_ShouldThrowException_WhenPlayerOrFriendNotFound() {
        // set up the expected value
        Long playerId = 1L;
        Long friendId = 2L;

        //  execute to get the  response
        when(playerRepository.existsById(playerId)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> friendService.addFriend(playerId, friendId));
        //check value with expected value 
        assertEquals("Invalid player or friend ID.", exception.getMessage());
        verify(friendRepository, never()).save(any(Friend.class));
    }

    @Test
    void addFriend_ShouldThrowException_WhenFriendshipExists() {
        // set up the expected value
        Long playerId = 1L;
        Long friendId = 2L;

        //  execute to get the  response
        when(playerRepository.existsById(playerId)).thenReturn(true);
        when(playerRepository.existsById(friendId)).thenReturn(true);
        when(friendDAO.friendExists(playerId, friendId)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> friendService.addFriend(playerId, friendId));
        //check value with expected value 
        assertEquals("Friendship already exists.", exception.getMessage());
        verify(friendRepository, never()).save(any(Friend.class));
    }

    @Test
    void getFriends_ShouldReturnFriendList() {
        // set up the expected value
        Long playerId = 1L;
        List<FriendInfoDTO> friends = new ArrayList<>();
        friends.add(new FriendInfoDTO("friend1"));
        friends.add(new FriendInfoDTO("friend2"));

        //  execute to get the  response
        when(friendDAO.findFriendsByPlayerId(playerId)).thenReturn(friends);

        List<FriendInfoDTO> result = friendService.getFriends(playerId);
        //check value with expected value 
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(friendDAO, times(1)).findFriendsByPlayerId(playerId);
    }

    @Test
    void removeFriend_ShouldRemoveFriend_WhenValidIds() {
        // set up the expected value
        Long playerId = 1L;
        Long friendId = 2L;

        //  execute to get the  response
        when(playerRepository.existsById(playerId)).thenReturn(true);
        when(playerRepository.existsById(friendId)).thenReturn(true);

        friendService.removeFriend(playerId, friendId);
        //check value with expected value 
        verify(friendDAO, times(1)).deleteByPlayerIdAndFriendId(playerId, friendId);
    }

    @Test
    void removeFriend_ShouldThrowException_WhenPlayerOrFriendNotFound() {
        // set up the expected value
        Long playerId = 1L;
        Long friendId = 2L;
        //  execute to get the  response
        when(playerRepository.existsById(playerId)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> friendService.removeFriend(playerId, friendId));
        //check value with expected value 
        assertEquals("Invalid player or friend ID.", exception.getMessage());
        verify(friendDAO, never()).deleteByPlayerIdAndFriendId(playerId, friendId);
    }
}
