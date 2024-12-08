package com.example.player_manager;

import com.example.player_manager.controller.FriendController;
import com.example.player_manager.dto.FriendInfoDTO;
import com.example.player_manager.dto.FriendRequestDTO;
import com.example.player_manager.service.FriendService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendControllerTest {

    @Mock
    private FriendService friendService;

    @InjectMocks
    private FriendController friendController;

    @Test
    void addFriend_ShouldReturnSuccessMessage_WhenValidRequest() {
        // set up the expected value
        FriendRequestDTO friendRequestDTO = new FriendRequestDTO(1L, 2L);

        //  execute to get the  response
        ResponseEntity<?> response = friendController.addFriend(1L,friendRequestDTO);

        //check value with expected value
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Friend added successfully.", response.getBody());
        verify(friendService, times(1)).addFriend(1L, 2L);
    }

    @Test
    void addFriend_ShouldReturnBadRequest_WhenInvalidRequest() {
        // set up the expected value
        FriendRequestDTO friendRequestDTO = new FriendRequestDTO(1L, 2L);
        doThrow(new IllegalArgumentException("Friendship already exists.")).when(friendService).addFriend(1L, 2L);

        //  execute to get the  response
        ResponseEntity<?> response = friendController.addFriend(1L,friendRequestDTO);

        //check value with expected value 
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Friendship already exists.", response.getBody());
        verify(friendService, times(1)).addFriend(1L, 2L);
    }

    @Test
    void getFriends_ShouldHandleNullableFields() {
        // set up the expected value
        Long playerId = 1L;
        List<FriendInfoDTO> friends = List.of(new FriendInfoDTO("Friend1"), new FriendInfoDTO("Friend2", 0, 0));
        when(friendService.getFriends(playerId)).thenReturn(friends);
    
        //  execute to get the  response
        ResponseEntity<?> response = friendController.getFriends(playerId);
    

        //check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(friends, response.getBody());
        verify(friendService, times(1)).getFriends(playerId);
    }
    

    @Test
    void getFriends_ShouldReturnNotFound_WhenNoFriendsExist() {
        // set up the expected value
        Long playerId = 1L;
        when(friendService.getFriends(playerId)).thenReturn(Collections.emptyList());

        //  execute to get the  response
        ResponseEntity<?> response = friendController.getFriends(playerId);

        //check value with expected value 
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("The player has no friends.", response.getBody());
        verify(friendService, times(1)).getFriends(playerId);
    }

    @Test
    void removeFriend_ShouldReturnSuccessMessage_WhenValidRequest() {
        // set up the expected value
        FriendRequestDTO friendRequestDTO = new FriendRequestDTO(1L, 2L);

        //  execute to get the  response
        ResponseEntity<?> response = friendController.removeFriend(1L,friendRequestDTO);

        //check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Friend Removed successfully.", response.getBody());
        verify(friendService, times(1)).removeFriend(1L, 2L);
    }

    @Test
    void removeFriend_ShouldReturnBadRequest_WhenInvalidRequest() {
        // set up the expected value
        FriendRequestDTO friendRequestDTO = new FriendRequestDTO(1L, 2L);
        doThrow(new IllegalArgumentException("Friendship does not exist.")).when(friendService).removeFriend(1L, 2L);

        //  execute to get the  response
        ResponseEntity<?> response = friendController.removeFriend(1L,friendRequestDTO);

        //check value with expected value 
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Friendship does not exist.", response.getBody());
        verify(friendService, times(1)).removeFriend(1L, 2L);
    }
}
