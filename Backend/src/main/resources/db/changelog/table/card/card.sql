--liquibase formatted sql

--changeset karimr19:card_table runOnChange:true
CREATE TABLE IF NOT EXISTS card
(
    id        BIGINT NOT NULL PRIMARY KEY,
    image     TEXT   NOT NULL,
    player_id BIGINT NOT NULL,
    game_id   BIGINT NOT NULL,
    status    TEXT   NOT NULL,
    CONSTRAINT card_fk_game FOREIGN KEY (game_id) REFERENCES game (id)
);