package com.example.player_manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.player_manager.dto.PlayerDTO;
import com.example.player_manager.entity.Player;
import com.example.player_manager.repository.PlayerRepository;
import com.example.player_manager.service.PlayerService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    public PlayerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePlayer() {
        // Arrange
        Player player = new Player();
        player.setUsername("player1");
        player.setEmail("player1@example.com");
        player.setLevel(1);
        player.setTotalPoints(100);

        when(playerRepository.save(player)).thenReturn(player);

        // Act
        Player createdPlayer = playerService.createPlayer(player);

        // Assert
        assertEquals("player1", createdPlayer.getUsername());
        assertEquals("player1@example.com", createdPlayer.getEmail());
        assertEquals(1, createdPlayer.getLevel());
        assertEquals(100, createdPlayer.getTotalPoints());
        System.out.println("Test createPlayer passed: " + createdPlayer.getUsername());

    }

    @Test
    public void testGetPlayerInfo() {
    // Arrange
    Player player = new Player();
    player.setId(1L);
    player.setUsername("player1");
    player.setLevel(46);
    player.setTotalPoints(129);

    when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

    // Act
    PlayerDTO result = playerService.getPlayerInfo(1L);

    // Assert
    assertEquals("player1", result.getUsername());
    assertEquals(46, result.getLevel());
    assertEquals(129, result.getTotalPoints());
    }
    
}
