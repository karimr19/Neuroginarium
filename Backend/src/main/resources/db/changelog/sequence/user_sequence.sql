--liquibase formatted sql

--changeset karimr19:user_sequence
CREATE SEQUENCE IF NOT EXISTS user_seq;