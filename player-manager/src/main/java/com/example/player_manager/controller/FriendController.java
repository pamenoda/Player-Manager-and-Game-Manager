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
    public ResponseEntity<String> addFriend(@RequestBody FriendRequestDTO friendRequestDTO) {
        friendService.addFriend(friendRequestDTO.getPlayerId(), friendRequestDTO.getFriendId());
        return ResponseEntity.ok("Friend added successfully.");
    }

    // get all friends of a player 
    @GetMapping
    public ResponseEntity<?> getFriends(@PathVariable Long Id) {
        List<FriendInfoDTO> friends = friendService.getFriends(Id);

        if (friends.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The player has no friends.");
        }
        return ResponseEntity.ok(friends);
    }

    // delete friends
    @DeleteMapping
    public ResponseEntity<String> removeFriend(@RequestBody FriendRequestDTO friendRequestDTO) {
        friendService.removeFriend(friendRequestDTO.getPlayerId(), friendRequestDTO.getFriendId());
        return ResponseEntity.ok("Friend removed successfully.");
    }
}
