-- Use the database
USE player_management;

-- Create the Player table
CREATE TABLE IF NOT EXISTS Player (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier
    username VARCHAR(255) NOT NULL UNIQUE, -- Unique username
    email VARCHAR(255) NOT NULL, -- Player's email
    level INT DEFAULT 0, -- Player level
    total_points INT DEFAULT 0 -- Total points of the player
);

-- Create the Friend table
CREATE TABLE IF NOT EXISTS Friend (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier
    player_id BIGINT NOT NULL, -- Reference to the main player
    friend_id BIGINT NOT NULL, -- Reference to the friend
    FOREIGN KEY (player_id) REFERENCES Player(id) ON DELETE CASCADE, -- Deletes friendships if the main player is deleted
    FOREIGN KEY (friend_id) REFERENCES Player(id) ON DELETE CASCADE -- Deletes references if the friend is deleted
);
