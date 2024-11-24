package com.example.player_manager.dto;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class FriendInfoDTO {
    @Nullable
    private String username;

    @Nullable
    private int level;

    @Nullable
    private int totalPoints;

}
