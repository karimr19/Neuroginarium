--liquibase formatted sql

--changeset karimr19:vote_sequence
CREATE SEQUENCE IF NOT EXISTS vote_seq;