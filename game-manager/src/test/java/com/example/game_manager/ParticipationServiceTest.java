package com.example.game_manager;

import com.example.game_manager.dao.IParticipationDAO;
import com.example.game_manager.dto.CreateParticipationDTO;
import com.example.game_manager.entity.Participation;
import com.example.game_manager.service.IExternalApiService;
import com.example.game_manager.service.IGameService;
import com.example.game_manager.service.ParticipationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParticipationServiceTest {

    @Mock
    private IParticipationDAO participationDAO;

    @Mock
    private IGameService gameService;

    @Mock
    private IExternalApiService externalApiService;

    @InjectMocks
    private ParticipationService participationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveParticipation_WhenValidInputs() {
        // Arrange: Set up valid participation data
        CreateParticipationDTO participationDTO = new CreateParticipationDTO(1L, 1L, 100, true);
        Participation expectedParticipation = new Participation(1L, 1L, 100, true);

        when(gameService.validateGameExists(participationDTO.getGameId())).thenReturn(null);
        when(externalApiService.checkIfPlayerExists(participationDTO.getPlayerId())).thenReturn(true);
        when(participationDAO.existsByGameIdAndPlayerId(participationDTO.getGameId(), participationDTO.getPlayerId()))
                .thenReturn(false);
        when(participationDAO.save(any(Participation.class))).thenReturn(expectedParticipation);

        //  execute to get the  responsed
        Participation actualParticipation = participationService.saveParticipation(participationDTO);

        //check value with expected value 
        assertNotNull(actualParticipation);
        assertEquals(expectedParticipation, actualParticipation);
        verify(participationDAO).save(any(Participation.class));
        verify(externalApiService).updatePlayerStats(participationDTO.getPlayerId(), participationDTO.getScore());
    }

    @Test
    void shouldThrowException_WhenPlayerDoesNotExist() {
        // set up the expected value
        CreateParticipationDTO participationDTO = new CreateParticipationDTO(1L, 1L, 100, true);

        //  execute to get the  response
        when(gameService.validateGameExists(participationDTO.getGameId())).thenReturn(null);
        when(externalApiService.checkIfPlayerExists(participationDTO.getPlayerId())).thenReturn(false);

        //check value with expected value 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> participationService.saveParticipation(participationDTO));
        assertTrue(exception.getMessage().contains("Player with ID 1 does not exist."));
    }

    @Test
    void shouldThrowException_WhenPlayerAlreadyParticipated() {
        // set up the expected value
        CreateParticipationDTO participationDTO = new CreateParticipationDTO(1L, 1L, 100, true);

        //  execute to get the  response
        when(gameService.validateGameExists(participationDTO.getGameId())).thenReturn(null);
        when(externalApiService.checkIfPlayerExists(participationDTO.getPlayerId())).thenReturn(true);
        when(participationDAO.existsByGameIdAndPlayerId(participationDTO.getGameId(), participationDTO.getPlayerId()))
                .thenReturn(true);

        //check value with expected value 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> participationService.saveParticipation(participationDTO));
        assertTrue(exception.getMessage().contains("Player with ID 1 has already participated in the game"));
    }

    @Test
    void shouldReturnParticipation_WhenParticipationExists() {
        // set up the expected value
        Long participationId = 1L;
        Participation expectedParticipation = new Participation(1L, 1L, 100, true);
        when(participationDAO.findById(participationId)).thenReturn(Optional.of(expectedParticipation));

        //  execute to get the  response
        Participation actualParticipation = participationService.findParticipationById(participationId);

       //check value with expected value 
        assertNotNull(actualParticipation);
        assertEquals(expectedParticipation, actualParticipation);
        verify(participationDAO).findById(participationId);
    }

    @Test
    void shouldThrowException_WhenParticipationDoesNotExist() {
        // set up the expected value
        Long participationId = 1L;
        when(participationDAO.findById(participationId)).thenReturn(Optional.empty());

        //  execute to get the  response
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> participationService.findParticipationById(participationId));

        //check value with expected value 
        assertTrue(exception.getMessage().contains("Participation with ID 1 not found."));
    }

    @Test
    void shouldDeleteParticipation_WhenExists() {
        // set up the expected value
        Long participationId = 1L;
        Participation participation = new Participation(1L, 1L, 100, true);
        when(participationDAO.findById(participationId)).thenReturn(Optional.of(participation));

        //  execute to get the  response
        participationService.deleteParticipation(participationId);

       //check value with expected value 
        verify(participationDAO).deleteById(participationId);
    }

    @Test
    void shouldThrowException_WhenParticipationToDeleteDoesNotExist() {
        // set up the expected value
        Long participationId = 1L;
        //  execute to get the  response
        when(participationDAO.findById(participationId)).thenReturn(Optional.empty());

       //check value with expected value 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> participationService.deleteParticipation(participationId));
        assertTrue(exception.getMessage().contains("Participation with ID 1 not found."));
    }
}
