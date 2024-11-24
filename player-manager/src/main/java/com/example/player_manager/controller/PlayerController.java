package com.example.player_manager.controller;
import com.example.player_manager.dto.PlayerDTO;
import com.example.player_manager.entity.Player;
import com.example.player_manager.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    // créer un joueur 
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.createPlayer(player);
        return ResponseEntity.ok(createdPlayer);
    }

    // Modifier un joueur
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO updatePlayerDTO) 
    {
        // we fetch the user 
        Player player = playerService.findById(id);

        // Vérify every field of player 
        if (updatePlayerDTO.getUsername() != null) player.setUsername(updatePlayerDTO.getUsername());
        if (updatePlayerDTO.getLevel() != 0) player.setLevel(updatePlayerDTO.getLevel());
        if (updatePlayerDTO.getTotalPoints() != 0) player.setTotalPoints(updatePlayerDTO.getTotalPoints());
    
        return ResponseEntity.ok(playerService.updatePlayer(id, player));
    }

    //Supprimer un joueur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
 

}
