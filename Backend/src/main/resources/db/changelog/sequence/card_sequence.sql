--liquibase formatted sql

--changeset karimr19:card_sequence
CREATE SEQUENCE IF NOT EXISTS card_seq;