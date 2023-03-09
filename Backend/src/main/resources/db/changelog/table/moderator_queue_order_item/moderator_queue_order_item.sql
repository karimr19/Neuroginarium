--liquibase formatted sql

--changeset karimr19:email_token_table runOnChange:true
CREATE TABLE IF NOT EXISTS moderator_queue_order_item
(
    id          BIGINT NOT NULL PRIMARY KEY,
    game_id     BIGINT NOT NULL,
    player_id   BIGINT NOT NULL,
    queue_order INT    NOT NULL
);