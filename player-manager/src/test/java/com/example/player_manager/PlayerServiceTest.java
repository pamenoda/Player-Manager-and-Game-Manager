package com.example.player_manager;

import com.example.player_manager.dao.IPlayerDAO;
import com.example.player_manager.dto.PlayerDTO;
import com.example.player_manager.entity.Player;
import com.example.player_manager.service.PlayerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private IPlayerDAO playerDAO;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlayer_ShouldReturnCreatedPlayer() {
        // set up the expected value
        Player player = new Player();
        player.setUsername("testUser");
        player.setLevel(1);
        player.setTotalPoints(100);

        //  execute to get the  response
        when(playerDAO.save(player)).thenReturn(player);

        Player createdPlayer = playerService.createPlayer(player);
        //check value with expected value 
        assertNotNull(createdPlayer);
        assertEquals("testUser", createdPlayer.getUsername());
        verify(playerDAO, times(1)).save(player);
    }

    @Test
    void findById_ShouldReturnPlayer_WhenPlayerExists() {
        // set up the expected value
        Long playerId = 1L;
        Player player = new Player();
        player.setId(playerId);

        //  execute to get the  response
        when(playerDAO.findById(playerId)).thenReturn(Optional.of(player));

        Player foundPlayer = playerService.findById(playerId);
        //check value with expected value 
        assertNotNull(foundPlayer);
        assertEquals(playerId, foundPlayer.getId());
        verify(playerDAO, times(1)).findById(playerId);
    }

    @Test
    void findById_ShouldThrowException_WhenPlayerNotFound() {
        // set up the expected value
        Long playerId = 1L;
        //  execute to get the  response
        when(playerDAO.findById(playerId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> playerService.findById(playerId));
        //check value with expected value 
        assertEquals("Player not found", exception.getMessage());
        verify(playerDAO, times(1)).findById(playerId);
    }

    @Test
    void updatePlayer_ShouldUpdateAndReturnPlayer() {
        // set up the expected value
        Long playerId = 1L;
        Player existingPlayer = new Player();
        existingPlayer.setId(playerId);
        existingPlayer.setUsername("oldUser");
        existingPlayer.setLevel(1);
        existingPlayer.setTotalPoints(50);

        PlayerDTO updateDTO = new PlayerDTO();
        updateDTO.setUsername("newUser");
        updateDTO.setLevel(2);
        updateDTO.setTotalPoints(100);

        //  execute to get the  response
        when(playerDAO.findById(playerId)).thenReturn(Optional.of(existingPlayer));
        when(playerDAO.save(existingPlayer)).thenReturn(existingPlayer);

        Player updatedPlayer = playerService.updatePlayer(playerId, updateDTO);

        //check value with expected value 
        assertNotNull(updatedPlayer);
        assertEquals("newUser", updatedPlayer.getUsername());
        assertEquals(2, updatedPlayer.getLevel());
        assertEquals(100, updatedPlayer.getTotalPoints());
        verify(playerDAO, times(1)).findById(playerId);
        verify(playerDAO, times(1)).save(existingPlayer);
    }

    @Test
    void updatePlayerStats_ShouldUpdateStatsCorrectly() {
        // set up the expected value
        Player player = new Player();
        player.setId(1L);
        player.setLevel(2);         
        player.setTotalPoints(90); 

        Mockito.when(playerDAO.findById(1L)).thenReturn(Optional.of(player));

        //  execute to get the  response
        playerService.updatePlayerStats(1L, 60); 
        int expectedTotalPoints = 90 + 60;
        int expectedLevel = expectedTotalPoints / 50; 

        //check value with expected value 
        assertEquals(expectedLevel, player.getLevel()); 
        assertEquals(expectedTotalPoints, player.getTotalPoints()); 
    }

    @Test
    void deletePlayer_ShouldDeletePlayer_WhenExists() {
        // set up the expected value
        Long playerId = 1L;

        //  execute to get the  response
        when(playerDAO.existsById(playerId)).thenReturn(true);

        playerService.deletePlayer(playerId);

        //check value with expected value 
        verify(playerDAO, times(1)).existsById(playerId);
        verify(playerDAO, times(1)).deleteById(playerId);
    }

    @Test
    void deletePlayer_ShouldThrowException_WhenPlayerNotFound() {
        // set up the expected value
        Long playerId = 1L;

        //  execute to get the  response
        when(playerDAO.existsById(playerId)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> playerService.deletePlayer(playerId));

        //check value with expected value 
        assertEquals("Player with ID " + playerId + " not found.", exception.getMessage());
        verify(playerDAO, times(1)).existsById(playerId);
        verify(playerDAO, never()).deleteById(playerId);
    }

    @Test
    void getPlayerInfo_ShouldReturnPlayerDTO_WhenPlayerExists() {
        // set up the expected value
        Long playerId = 1L;
        Player player = new Player();
        player.setId(playerId);
        player.setUsername("testUser");
        player.setLevel(2);
        player.setTotalPoints(100);

        //  execute to get the  response
        when(playerDAO.findById(playerId)).thenReturn(Optional.of(player));

        PlayerDTO playerDTO = playerService.getPlayerInfo(playerId);

        //check value with expected value 
        assertNotNull(playerDTO);
        assertEquals("testUser", playerDTO.getUsername());
        assertEquals(2, playerDTO.getLevel());
        assertEquals(100, playerDTO.getTotalPoints());
        verify(playerDAO, times(1)).findById(playerId);
    }
}
