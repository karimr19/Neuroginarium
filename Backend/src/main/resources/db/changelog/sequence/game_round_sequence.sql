--liquibase formatted sql

--changeset karimr19:game_round_sequence
CREATE SEQUENCE IF NOT EXISTS game_round_seq;