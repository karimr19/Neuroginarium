--liquibase formatted sql

--changeset karimr19:game_sequence
CREATE SEQUENCE IF NOT EXISTS game_seq;