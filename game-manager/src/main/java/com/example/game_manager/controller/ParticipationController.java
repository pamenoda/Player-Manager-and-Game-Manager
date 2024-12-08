package com.example.game_manager.controller;

import com.example.game_manager.dto.CreateParticipationDTO;
import com.example.game_manager.entity.Participation;
import com.example.game_manager.service.IParticipationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games/{gameId}/participations")
public class ParticipationController {

    private final IParticipationService participationService;

    public ParticipationController(IParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping
    public ResponseEntity<?> registerParticipation(@PathVariable Long gameId, @RequestBody CreateParticipationDTO participationDTO) {
        try {
            participationDTO.setGameId(gameId); 
            participationService.saveParticipation(participationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Participation added successfully");
        } catch (IllegalArgumentException e) {
            // Handle logical errors, such as game or player not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipation(@PathVariable Long id) {
        try {
            Participation participation = participationService.findParticipationById(id);
            return ResponseEntity.ok(participation);
        } catch (IllegalArgumentException e) {
            // Handle logical errors, such as participation not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
