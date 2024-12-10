package com.example.game_manager;

import com.example.game_manager.dao.IGameDAO;
import com.example.game_manager.dto.CreateGameDTO;
import com.example.game_manager.dto.GetGameDTO;
import com.example.game_manager.dto.UpdateGameDTO;
import com.example.game_manager.entity.Game;
import com.example.game_manager.entity.GameType;
import com.example.game_manager.service.GameService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private IGameDAO gameDAO;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createGame_ShouldThrowException_WhenInvalidGameType() {
        // set up the expected value
        CreateGameDTO createGameDTO = new CreateGameDTO(null, 100, 1L);
        // execute 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gameService.createGame(createGameDTO));

        // check value with expected value 
        assertTrue(exception.getMessage().contains("GameType cannot be null."));
    }

  

    @Test
    void getGameById_ShouldReturnGame_WhenExists() {
        // set up the expected value
        Long gameId = 1L;
        Game game = new Game(LocalDate.now(), GameType.TOURNAMENT, 100, 1L);

        // execute 
        when(gameDAO.findById(gameId)).thenReturn(Optional.of(game));
        GetGameDTO gameDTO = gameService.getGameById(gameId);

        // check value with expected value 
        assertNotNull(gameDTO);
        assertEquals(game.getGameType(), gameDTO.getGameType());
        verify(gameDAO, times(1)).findById(gameId);
    }

    @Test
    void updateGame_ShouldUpdateGame_WhenValidInputs() {
        // set up the expected value
        Long gameId = 1L;
        Game existingGame = new Game(LocalDate.now(), GameType.TOURNAMENT, 100, 1L);
        UpdateGameDTO updateGameDTO = new UpdateGameDTO(GameType.MULTIPLAYER, 150);

        // execute 
        when(gameDAO.findById(gameId)).thenReturn(Optional.of(existingGame));
        when(gameDAO.save(existingGame)).thenReturn(existingGame);
        Game updatedGame = gameService.updateGame(gameId, updateGameDTO);

        // check value with expected value 
        assertNotNull(updatedGame);
        assertEquals(updateGameDTO.getGameType(), updatedGame.getGameType());
        verify(gameDAO, times(1)).save(existingGame);
    }
}
