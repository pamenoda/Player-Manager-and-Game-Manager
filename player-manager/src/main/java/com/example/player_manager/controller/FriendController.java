package com.example.player_manager.controller;

import com.example.player_manager.dto.FriendInfoDTO;
import com.example.player_manager.dto.FriendRequestDTO;
import com.example.player_manager.service.FriendService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players/{Id}/friends")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    // add a friend
    @PostMapping
    public ResponseEntity<?> addFriend(@PathVariable Long Id,@RequestBody FriendRequestDTO friendRequestDTO) {
        try {
            if (Id.equals(friendRequestDTO.getPlayerId()))
            {
                friendService.addFriend(friendRequestDTO.getPlayerId(), friendRequestDTO.getFriendId());
                return ResponseEntity.ok("Friend added successfully.");
            }
            else{
                return ResponseEntity.ok("same id for the path and playerID");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the friend: " + e.getMessage());
        }
    }

    // get all friends of a player 
    @GetMapping
    public ResponseEntity<?> getFriends(@PathVariable Long Id) {
        try {
            List<FriendInfoDTO> friends = friendService.getFriends(Id);

            if (friends.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The player has no friends.");
            }
            return ResponseEntity.ok(friends);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the friends: " + e.getMessage());
        }
    }

    // delete friends
    @DeleteMapping
    public ResponseEntity<?> removeFriend(@PathVariable Long Id,@RequestBody FriendRequestDTO friendRequestDTO) {
        try {
            if (Id.equals(friendRequestDTO.getPlayerId()))
            {
                friendService.removeFriend(friendRequestDTO.getPlayerId(), friendRequestDTO.getFriendId());
                return ResponseEntity.ok("Friend Removed successfully.");
            }
            else{
                return ResponseEntity.ok("same id for the path and playerID");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while removing the friend: " + e.getMessage());
        }
    }
}
