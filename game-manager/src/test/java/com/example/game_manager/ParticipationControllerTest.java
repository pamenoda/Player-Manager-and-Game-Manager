package com.example.game_manager;

import com.example.game_manager.controller.ParticipationController;
import com.example.game_manager.dto.CreateParticipationDTO;
import com.example.game_manager.entity.Participation;
import com.example.game_manager.service.IParticipationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ParticipationControllerTest {

    @Mock
    private IParticipationService participationService;

    @InjectMocks
    private ParticipationController participationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCreatedResponse_WhenParticipationIsRegistered() {
        // set up the expected value
        Long gameId = 1L;
        CreateParticipationDTO participationDTO = new CreateParticipationDTO();
        participationDTO.setPlayerId(1L);
        participationDTO.setScore(100);
        participationDTO.setWin(true);

        Participation expectedParticipation = new Participation(gameId, participationDTO.getPlayerId(), participationDTO.getScore(), participationDTO.isWin());
        when(participationService.saveParticipation(any(CreateParticipationDTO.class))).thenReturn(expectedParticipation);

        //  execute to get the  response
        ResponseEntity<?> response = participationController.registerParticipation(gameId, participationDTO);

        // check value with expected value 
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Participation added successfully", response.getBody());
        verify(participationService, times(1)).saveParticipation(any(CreateParticipationDTO.class));
    }

    @Test
    void shouldReturnBadRequest_WhenIllegalArgumentExceptionThrown() {
        // set up the expected value
        Long gameId = 1L;
        CreateParticipationDTO participationDTO = new CreateParticipationDTO();
        participationDTO.setPlayerId(1L);
        participationDTO.setScore(100);
        participationDTO.setWin(true);

        doThrow(new IllegalArgumentException("Player does not exist")).when(participationService)
                .saveParticipation(any(CreateParticipationDTO.class));

        // execute to get the  response
        ResponseEntity<?> response = participationController.registerParticipation(gameId, participationDTO);

        // check value with expected value 
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Player does not exist", response.getBody());
        verify(participationService, times(1)).saveParticipation(any(CreateParticipationDTO.class));
    }

    @Test
    void shouldReturnInternalServerError_WhenUnexpectedErrorOccursInRegisterParticipation() {
        // set up the expected value
        Long gameId = 1L;
        CreateParticipationDTO participationDTO = new CreateParticipationDTO();
        participationDTO.setPlayerId(1L);
        participationDTO.setScore(100);
        participationDTO.setWin(true);

        doThrow(new RuntimeException("Unexpected error")).when(participationService)
                .saveParticipation(any(CreateParticipationDTO.class));

        //  execute to get the  response
        ResponseEntity<?> response = participationController.registerParticipation(gameId, participationDTO);

        // check value with expected value 
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("An unexpected error occurred: Unexpected error", response.getBody());
        verify(participationService, times(1)).saveParticipation(any(CreateParticipationDTO.class));
    }

    @Test
    void shouldReturnParticipation_WhenFound() {
        // set up the expected value
        Long participationId = 1L;
        Participation expectedParticipation = new Participation(1L, 1L, 100, true);

        when(participationService.findParticipationById(participationId)).thenReturn(expectedParticipation);

        //  execute to get the  response
        ResponseEntity<?> response = participationController.getParticipation(participationId);

        // check value with expected value 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedParticipation, response.getBody());
        verify(participationService, times(1)).findParticipationById(eq(participationId));
    }

    @Test
    void shouldReturnNotFound_WhenParticipationDoesNotExist() {
        // set up the expected value
        Long participationId = 1L;

        doThrow(new IllegalArgumentException("Participation with ID 1 not found")).when(participationService)
                .findParticipationById(participationId);

        //  execute to get the  response
        ResponseEntity<?> response = participationController.getParticipation(participationId);

        //check value with expected value 
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Participation with ID 1 not found", response.getBody());
        verify(participationService, times(1)).findParticipationById(eq(participationId));
    }

    @Test
    void shouldReturnInternalServerError_WhenUnexpectedErrorOccursInGetParticipation() {
        // set up the expected value
        Long participationId = 1L;

        doThrow(new RuntimeException("Unexpected error")).when(participationService)
                .findParticipationById(participationId);

        // execute to get the  response
        ResponseEntity<?> response = participationController.getParticipation(participationId);

        // check value with expected value 
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("An unexpected error occurred: Unexpected error", response.getBody());
        verify(participationService, times(1)).findParticipationById(eq(participationId));
    }
}
