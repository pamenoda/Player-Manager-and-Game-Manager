package com.example.player_manager;

import com.example.player_manager.controller.PlayerController;
import com.example.player_manager.dto.PlayerDTO;
import com.example.player_manager.entity.Player;
import com.example.player_manager.service.PlayerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlayer_ShouldReturnCreatedMessage_WhenSuccessful() {
        // set up the expected value
        Player player = new Player();
        when(playerService.createPlayer(player)).thenReturn(player);

        //  execute to get the  response
        ResponseEntity<?> response = playerController.createPlayer(player);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Created successfully", response.getBody());
    }

    @Test
    void updatePlayerStats_ShouldUpdateStats_WhenValidData() {
        // set up the expected value
        Long playerId = 1L;
        Map<String, Object> stats = new HashMap<>();
        stats.put("scoreToAdd", 50);

        //  execute to get the  response
        ResponseEntity<?> response = playerController.updatePlayerStats(playerId, stats);

        //check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Player stats updated successfully.", response.getBody());
        verify(playerService, times(1)).updatePlayerStats(playerId, 50);
    }

    @Test
    void updatePlayerStats_ShouldReturnBadRequest_WhenScoreToAddMissing() {
        // set up the expected value
        Long playerId = 1L;
        Map<String, Object> stats = new HashMap<>();

        //  execute to get the  response
        ResponseEntity<?> response = playerController.updatePlayerStats(playerId, stats);

        //check value with expected value 
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Missing required field: scoreToAdd", response.getBody());
        verify(playerService, never()).updatePlayerStats(anyLong(), anyInt());
    }

    @Test
    void updatePlayer_ShouldReturnSuccess_WhenPlayerUpdated() {
        // set up the expected value
        Long playerId = 1L;
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setUsername("newUser");

        //  execute to get the  response
        ResponseEntity<?> response = playerController.updatePlayer(playerId, playerDTO);

        //check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated successfully", response.getBody());
        verify(playerService, times(1)).updatePlayer(playerId, playerDTO);
    }

    @Test
    void getPlayer_ShouldReturnPlayer_WhenPlayerExists() {
        // set up the expected value
        Long playerId = 1L;
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setUsername("existingUser");
        when(playerService.getPlayerInfo(playerId)).thenReturn(playerDTO);

        //  execute to get the  response
        ResponseEntity<?> response = playerController.getPlayer(playerId);

        //check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(playerDTO, response.getBody());
        verify(playerService, times(1)).getPlayerInfo(playerId);
    }

    @Test
    void getPlayer_ShouldReturnNotFound_WhenPlayerDoesNotExist() {
        // set up the expected value
        Long playerId = 1L;
        when(playerService.getPlayerInfo(playerId)).thenThrow(new IllegalArgumentException("Player not found"));

        //  execute to get the  response
        ResponseEntity<?> response = playerController.getPlayer(playerId);

        //check value with expected value 
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Player not found", response.getBody());
        verify(playerService, times(1)).getPlayerInfo(playerId);
    }

    @Test
    void deletePlayer_ShouldReturnSuccess_WhenPlayerDeleted() {
        // set up the expected value
        Long playerId = 1L;

        //  execute to get the  response
        ResponseEntity<?> response = playerController.deletePlayer(playerId);

        //check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Player deleted successfully.", response.getBody());
        verify(playerService, times(1)).deletePlayer(playerId);
    }

    @Test
    void deletePlayer_ShouldReturnNotFound_WhenPlayerDoesNotExist() {
        // set up the expected value
        Long playerId = 1L;
        doThrow(new IllegalArgumentException("Player not found with ID: " + playerId))
                .when(playerService).deletePlayer(playerId);

        //  execute to get the  response
        ResponseEntity<?> response = playerController.deletePlayer(playerId);

        //check value with expected value 
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Player not found with ID: " + playerId, response.getBody());
        verify(playerService, times(1)).deletePlayer(playerId);
    }
}
