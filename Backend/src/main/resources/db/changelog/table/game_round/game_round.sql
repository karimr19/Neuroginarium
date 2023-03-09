--liquibase formatted sql

--changeset karimr19:game_round_table runOnChange:true
CREATE TABLE IF NOT EXISTS game_round
(
    id                     BIGINT NOT NULL PRIMARY KEY,
    association_creator_id INT    NOT NULL,
    association            TEXT   NOT NULL,
    status                 TEXT   NOT NULL,
    game_id                BIGINT NOT NULL,
    card_id                BIGINT NOT NULL,
    CONSTRAINT game_round_fk_game FOREIGN KEY (game_id) REFERENCES game (id)
);