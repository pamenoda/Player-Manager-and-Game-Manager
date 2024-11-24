package com.example.player_manager.controller;

import com.example.player_manager.dto.FriendInfoDTO;
import com.example.player_manager.dto.FriendRequestDTO;
import com.example.player_manager.service.FriendService;

import java.util.List;

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

    // get all friends
    @GetMapping
    public ResponseEntity<List<FriendInfoDTO>> getFriends(@PathVariable Long Id) {
        List<FriendInfoDTO> friends = friendService.getFriends(Id);
        return ResponseEntity.ok(friends);
    }

    // delete friends
    @DeleteMapping
    public ResponseEntity<String> removeFriend(@RequestBody FriendRequestDTO friendRequestDTO) {
        friendService.removeFriend(friendRequestDTO.getPlayerId(), friendRequestDTO.getFriendId());
        return ResponseEntity.ok("Friend removed successfully.");
    }
}
