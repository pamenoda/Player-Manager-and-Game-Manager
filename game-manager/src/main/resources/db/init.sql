-- Create the database
CREATE DATABASE IF NOT EXISTS game_management;

-- Use the database
USE game_management;

-- Create the Game table
CREATE TABLE IF NOT EXISTS Game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for the game
    date DATETIME NOT NULL, -- Date of the game
    game_type VARCHAR(50) NOT NULL, -- Type of the game (e.g., enum value)
    max_score INT NOT NULL, -- Maximum score for the game
    host_id BIGINT NOT NULL -- ID of the host (player hosting the game)
);

-- Create the Participation table
CREATE TABLE IF NOT EXISTS Participation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for the participation
    game_id BIGINT NOT NULL, -- Reference to the game
    player_id BIGINT NOT NULL, -- Reference to the player
    score INT DEFAULT 0, -- Score obtained in the game
    is_win BOOLEAN DEFAULT FALSE, -- Whether the player won the game
    FOREIGN KEY (game_id) REFERENCES Game(id) ON DELETE CASCADE, -- Deletes participation records if the game is deleted
    FOREIGN KEY (player_id) REFERENCES player_management.Player(id) ON DELETE CASCADE -- Links to the player from the first database
);
