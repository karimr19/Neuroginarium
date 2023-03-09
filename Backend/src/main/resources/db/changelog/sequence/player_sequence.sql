--liquibase formatted sql

--changeset karimr19:player_sequence
CREATE SEQUENCE IF NOT EXISTS player_seq;