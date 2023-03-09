--liquibase formatted sql

--changeset karimr19:vote_table runOnChange:true
CREATE TABLE IF NOT EXISTS vote
(
    id        BIGINT NOT NULL PRIMARY KEY,
    player_id BIGINT NOT NULL,
    card_id   BIGINT NOT NULL,
    round_id  BIGINT NOT NULL
);