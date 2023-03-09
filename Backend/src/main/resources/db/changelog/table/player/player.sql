--liquibase formatted sql

--changeset karimr19:player_table runOnChange:true
CREATE TABLE IF NOT EXISTS player
(
    id      BIGINT NOT NULL PRIMARY KEY,
    user_id INT    NOT NULL,
    game_id BIGINT NOT NULL,
    points  INT    NOT NULL,
    CONSTRAINT player_fk_game FOREIGN KEY (game_id) REFERENCES game (id)
);