package com.example.player_manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    
}
