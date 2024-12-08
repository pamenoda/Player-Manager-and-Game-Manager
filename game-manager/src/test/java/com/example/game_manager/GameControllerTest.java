package com.example.game_manager;

import com.example.game_manager.controller.GameController;
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
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_ShouldReturnCreatedResponse_WhenValidInputs() {
        // set up the expected value
        CreateGameDTO createGameDTO = new CreateGameDTO(GameType.MULTIPLAYER, 100, 1L);
  
        // execute 
        ResponseEntity<?> response = gameController.createGame(createGameDTO);

        //check value with expected value 
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Created Successfully", response.getBody());
        verify(gameService, times(1)).createGame(any(CreateGameDTO.class));
    }

    @Test
    void createGame_ShouldReturnBadRequest_WhenInvalidInputs() {
        // set up the expected value
        CreateGameDTO createGameDTO = new CreateGameDTO(null, 100, 1L);
        when(gameService.createGame(any(CreateGameDTO.class))).thenThrow(new IllegalArgumentException("Invalid game type"));

        // execute 
        ResponseEntity<?> response = gameController.createGame(createGameDTO);

        //check value with expected value 
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid game type", response.getBody());
        verify(gameService, times(1)).createGame(any(CreateGameDTO.class));
    }

    @Test
    void getGameById_ShouldReturnGame_WhenGameExists() {
        // set up the expected value
        Long gameId = 1L;
        GetGameDTO getGameDTO = new GetGameDTO(LocalDate.now(), GameType.MULTIPLAYER, 100);
        when(gameService.getGameById(gameId)).thenReturn(getGameDTO);

        // execute 
        ResponseEntity<?> response = gameController.getGameById(gameId);

        //check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(getGameDTO, response.getBody());
        verify(gameService, times(1)).getGameById(gameId);
    }

    @Test
    void getGameById_ShouldReturnNotFound_WhenGameDoesNotExist() {
        // set up the expected value
        Long gameId = 1L;
        when(gameService.getGameById(gameId)).thenThrow(new IllegalArgumentException("Game not found"));

        // execute 
        ResponseEntity<?> response = gameController.getGameById(gameId);

        //check value with expected value 
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Game not found", response.getBody());
        verify(gameService, times(1)).getGameById(gameId);
    }

    @Test
    void updateGame_ShouldReturnSuccess_WhenValidInputs() {
        // set up the expected value
        Long gameId = 1L;
        UpdateGameDTO updateGameDTO = new UpdateGameDTO(GameType.SINGLE_PLAYER, 200);

         // execute 
        ResponseEntity<?> response = gameController.updateGame(gameId, updateGameDTO);

        //check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Game updated successfully.", response.getBody());
        verify(gameService, times(1)).updateGame(eq(gameId), any(UpdateGameDTO.class));
    }

    @Test
    void updateGame_ShouldReturnBadRequest_WhenInvalidInputs() {
        // set up the expected value
        Long gameId = 1L;
        UpdateGameDTO updateGameDTO = new UpdateGameDTO(GameType.SINGLE_PLAYER, 200);
        when(gameService.updateGame(eq(gameId), any(UpdateGameDTO.class))).thenThrow(new IllegalArgumentException("Invalid update data"));

        // execute 
        ResponseEntity<?> response = gameController.updateGame(gameId, updateGameDTO);

        //check value with expected value 
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid update data", response.getBody());
        verify(gameService, times(1)).updateGame(eq(gameId), any(UpdateGameDTO.class));
    }

    @Test
    void deleteGame_ShouldReturnSuccess_WhenGameExists() {
        // set up the expected value
        Long gameId = 1L;

        // execute 
        ResponseEntity<?> response = gameController.deleteGame(gameId);

        //check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Game deleted successfully.", response.getBody());
        verify(gameService, times(1)).deleteGame(gameId);
    }

    @Test
    void deleteGame_ShouldReturnBadRequest_WhenGameDoesNotExist() {
        // set up the expected value
        Long gameId = 1L;
        doThrow(new IllegalArgumentException("Game not found")).when(gameService).deleteGame(gameId);

        // execute 
        ResponseEntity<?> response = gameController.deleteGame(gameId);

        //check value with expected value 
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Game not found", response.getBody());
        verify(gameService, times(1)).deleteGame(gameId);
    }
}
