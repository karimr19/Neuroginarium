--liquibase formatted sql

--changeset karimr19:game_table runOnChange:true
CREATE TABLE IF NOT EXISTS game
(
    id
    BIGINT
    NOT
    NULL
    PRIMARY
    KEY,
    is_auto_game
    BOOLEAN
    NOT
    NULL,
    players_cnt
    INT
    NOT
    NULL,
    status
    TEXT
    NOT
    NULL,
    creation_date_time
    TIMESTAMP
    NOT
    NULL,
    round
    INT
    NOT
    NULL,
    token
    TEXT
);